package exampleslibrary.local;

import EntryPoint.Universe;
import constants.Consts;
import exception.*;
import metaclasses.Dashboard;
import metaclasses.Format;
import metaclasses.Visualization;
import metaclasses.concern.ConcernFactory;
import metaclasses.resource.Arity;
import metaclasses.resource.AtomicResource;
import metaclasses.resource.DataType;
import metaclasses.resource.Element;

import java.io.IOException;

import static model.exploitation.CodeGeneration.codeGeneration;

/**
 * Created by ivan on 10/09/2014.
 */
class Weight {
    public static void main(String[] args) throws IOException, GetUniqueElementOnNonCompleteConfiguration, BadIDException, VisitorException, UnhandledFamiliarException, ReductionException, EmptyUniverseException {


        /////
        //1//  Design the model of the wanted dashboard
        /////
        Dashboard dashboard = new Dashboard();
        ConcernFactory factory = new ConcernFactory();


        Visualization visu1 = new Visualization();

        AtomicResource resource = new AtomicResource(
                "Weight lost since 22th august",
                "Weight",
                Arity.Many,
                "http://users.polytech.unice.fr/~logre/resources/weight.me",
                Format.Custom,
                new Element("t", DataType.long_type),
                new Element("v",DataType.double_type));
        visu1.addResource(resource);
        AtomicResource resource1 = new AtomicResource(
                "Weight Goal to the 22th october",
                "WeightGoal",
                Arity.Many,
                "http://users.polytech.unice.fr/~logre/resources/weightgoal.me",
                Format.Custom,
                new Element("t", DataType.long_type),
                new Element("v",DataType.double_type));
        visu1.addResource(resource1);
        visu1.addConcern(factory.Continuous());
        dashboard.addVisualization(visu1);


        Visualization visu2 = new Visualization();
        AtomicResource resource2 = new AtomicResource(
                "Weight lost since 22th august",
                "Weight",
                Arity.Many,
                "http://users.polytech.unice.fr/~logre/resources/weight.me",
                Format.Custom,
                new Element("t", DataType.long_type),
                new Element("v",DataType.double_type));
        visu2.addResource(resource2);
        AtomicResource resource3 = new AtomicResource(
                "Weight Goal to the 22th october",
                "WeightGoal",
                Arity.Many,
                "http://users.polytech.unice.fr/~logre/resources/weightgoal.me",
                Format.Custom,
                new Element("t", DataType.long_type),
                new Element("v",DataType.double_type));
        visu2.addResource(resource3);
        visu2.addConcern(factory.Discrete());
        visu2.addConcern(factory.Variations());
        dashboard.addVisualization(visu2);

        /////
        //2//  Use feature model to find a suitable generable widget
        /////
        for(Visualization visu : dashboard.getVisualizationList()) {
            Universe univ = new Universe();
            univ.displayUniverseState();
            univ.reduceByConcerns(visu.getConcernNames());
            if (univ.isMinimal()) {
                visu.setLibraryName(univ.getLastLibraryName());
                visu.setWidgetName(univ.getLastWidgetName());
            }
        }

        /////
        //3//  Generation
        /////
        //Generation of the HTML code from the model
        codeGeneration(dashboard,Consts.RUNTIME_FOLDER+Consts.GENERATED_TARGET_FOLDER,"Weight.html");

    }
}
