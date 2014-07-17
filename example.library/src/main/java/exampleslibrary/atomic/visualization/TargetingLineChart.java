package exampleslibrary.atomic.visualization;

import EntryPoint.Universe;
import constants.Consts;
import exception.*;
import metaclasses.*;
import utils.FileOperation;

import java.io.IOException;

import static model.exploitation.CodeGeneration.codeGeneration;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
class TargetingLineChart {

    /*
     * This example means to illustrate the capability of designing single visualization dashboard with continuous data
     * It has to :
     *  - instantiate a valid model (conforms to the meta model)
     *  - reduce a configuration of the feature model according to the "continuous" criteria
     *  - generate the code of the resulting visualization
     */
    public static void main(String[] args) throws IOException, GetUniqueElementOnNonCompleteConfiguration, BadIDException, UnhandledDataFormatException, UnhandledFamiliarException, ReductionException, EmptyUniverseException {


         /////
        //1//  Design the model of the wanted dashboard
       /////
        Dashboard dashboard = new Dashboard();
        Data data = new Data(Consts.SPEED_SENML, Format.SenML);
        Concern concern = Concern.Continuous;
        Visualization visu = new Visualization(data, concern);
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
