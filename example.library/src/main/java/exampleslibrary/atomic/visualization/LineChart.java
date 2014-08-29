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

import static model.exploitation.CodeGeneration.codeGeneration;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
class LineChart {

    /*
     * This example means to illustrate the capability of designing single visualization dashboard with continuous data
     * It has to :
     *  - instantiate a valid model (conforms to the meta model)
     *  - reduce a configuration of the feature model according to the "continuous" criteria
     *  - generate the code of the resulting visualization
     */
    public static void main(String[] args) throws IOException, GetUniqueElementOnNonCompleteConfiguration, BadIDException, VisitorException, UnhandledFamiliarException, ReductionException, EmptyUniverseException {


         /////
        //1//  Design the model of the wanted dashboard
       /////
        Dashboard dashboard = new Dashboard();
        AtomicResource resource = new AtomicResource(
                "Ground speed of a bike in Oslo",
                "Speed",
                Arity.One,
                Consts.SPEED_SENML,
                Format.SenML,
                new Element("t", DataType.numerical),
                new Element("v",DataType.numerical));
        ConcernFactory factory = new ConcernFactory();
        Visualization visu = new Visualization(resource, factory.Continuous());
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
            String code = codeGeneration(dashboard);
            //Creation of the /product folder if it doesn't exist already
            FileOperation.setUpFolder(Consts.GENERATED_TARGET_FOLDER);
            //store the resulting visualization in a file named after the used concern
            FileOperation.fillFileFromObject(code, Consts.RUNTIME_FOLDER+Consts.GENERATED_TARGET_FOLDER+"Line.html");
        }
    }

}
