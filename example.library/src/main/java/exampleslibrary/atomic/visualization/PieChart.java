package exampleslibrary.atomic.visualization;

import EntryPoint.Universe;
import constants.Consts;
import exception.*;
import metaclasses.*;
import metaclasses.resource.Arity;
import metaclasses.resource.AtomicResource;
import metaclasses.concern.ConcernFactory;
import metaclasses.resource.DataType;
import metaclasses.resource.Element;
import utils.FileOperation;

import java.io.IOException;
import java.nio.file.Paths;

import static model.exploitation.CodeGeneration.codeGeneration;

/**
 * Created by ivan on 08/07/2014.
 */
public class PieChart {

    public static void main(String[] args) throws IOException, BadIDException, GetUniqueElementOnNonCompleteConfiguration, VisitorException, UnhandledFamiliarException, ReductionException, EmptyUniverseException {

         /////
        //1//  Design the model of the wanted dashboard
       /////
        Dashboard dashboard = new Dashboard();
        Visualization visu = new Visualization();
        AtomicResource resource = new AtomicResource(
                "External temperature in Oslo",
                "Proportion",
                Arity.One,
                Consts.CATEGORIZED_STACKED,
                Format.Stacked,
                new Element("range", DataType.textual_type),
                new Element("volume",DataType.double_type));
        visu.addResource(resource);
        visu.addConcern(new ConcernFactory().Proportion());
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

             /////
            //3//  Generation
           /////
            //Generation of the HTML code from the model
            codeGeneration(dashboard,Consts.RUNTIME_FOLDER+Consts.GENERATED_TARGET_FOLDER,"PieChart.html");
        }
    }
}
