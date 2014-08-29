package exampleslibrary.composed.visualization;

import EntryPoint.Universe;
import constants.Consts;
import exception.*;
import metaclasses.*;
import metaclasses.resource.Arity;
import metaclasses.resource.AtomicResource;
import metaclasses.concern.Concern;
import metaclasses.concern.ConcernFactory;
import metaclasses.resource.DataType;
import metaclasses.resource.Element;
import utils.FileOperation;

import java.io.IOException;

import static model.exploitation.CodeGeneration.codeGeneration;

/**
 * Created by ivan on 17/07/2014.
 */
public class MultipleLineChart {

    public static void main(String[] args) throws IOException, GetUniqueElementOnNonCompleteConfiguration, BadIDException, VisitorException, UnhandledFamiliarException, ReductionException, EmptyUniverseException {

        /////
        //1//  Design the model of the wanted dashboard
        /////
        Dashboard dashboard = new Dashboard();
        ConcernFactory factory = new ConcernFactory();

        // #1 visualization : Discrete & Extremum
        Visualization visu1 = new Visualization();
        AtomicResource resource = new AtomicResource(
                "External temperature in Oslo",
                "Temp",
                Arity.One,
                Consts.TEMP_SENML,
                Format.SenML,
                new Element("t", DataType.numerical),
                new Element("v",DataType.numerical));
        visu1.addResource(resource);
        visu1.addConcern(factory.Continuous());

        // #2 visualization : Continuous
        Visualization visu2 = new Visualization();
        AtomicResource resource2 = new AtomicResource(
                "Edited external temperature in Oslo",
                "NoTemp",
                Arity.One,
                Consts.TEMP_NEG_SENML,
                Format.SenML,
                new Element("t",DataType.numerical),
                new Element("v",DataType.numerical));
        Concern concern2 = factory.Continuous();
        visu2.addResource(resource2);
        visu2.addConcern(concern2);

        // #3 visualization : Continuous
        Visualization visu3 = new Visualization();
        AtomicResource resource3 = new AtomicResource(
                "Ground speed of a bike in Oslo",
                "Speed",
                Arity.One,
                Consts.SPEED_SENML,
                Format.SenML,
                new Element("t",DataType.numerical),
                new Element("v",DataType.numerical));
        Concern concern3 = factory.Continuous();
        visu3.addResource(resource3);
        visu3.addConcern(concern3);

        Visualization visu = Visualization.Fusion(Visualization.Fusion(visu1, visu2), visu3);
        dashboard.addVisualization(visu);

        /////
        //2//  Use feature model to find a suitable generable widget
        /////
        Universe univ = new Universe();
        univ.displayUniverseState();
        univ.reduceByConcerns(visu.getConcernNames());
        if (univ.isMinimal()) {
            visu.setLibraryName(univ.getLastLibraryName());
            visu.setWidgetName(univ.getLastWidgetName());
        }

        /////
        //3//  Generation
        /////
        //Generation of the HTML code from the model
        String code = codeGeneration(dashboard);
        //Creation of the /product folder if it doesn't exist already
        FileOperation.setUpFolder(Consts.GENERATED_TARGET_FOLDER);
        //store the resulting visualization in a file named after the used concern
        FileOperation.fillFileFromObject(code, Consts.RUNTIME_FOLDER+Consts.GENERATED_TARGET_FOLDER + "MultipleLine.html");
    }
}
