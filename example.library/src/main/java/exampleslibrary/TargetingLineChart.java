package exampleslibrary;

import EntryPoint.Library;
import EntryPoint.Reduction;
import constants.Consts;
import exception.BadIDException;
import exception.GetNameOnNonCompleteConfiguration;
import exception.UnhandledDataFormatException;
import metaclasses.*;
import utils.FileOperation;

import java.io.IOException;
import java.nio.file.Paths;

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
    public static void main(String[] args) throws IOException, GetNameOnNonCompleteConfiguration, BadIDException, UnhandledDataFormatException {

        //Design the model of the wanted dashboard
        Dashboard dashboard = new Dashboard();
        Data data = new Data(Consts.SPEED_SENML, Format.SenML);
        Concern concern = Concern.Continuous;
        Visualization visu = new Visualization(data, concern);
        dashboard.addVisualization(visu);

        //Use feature model to find a suitable generable widget
        Library lib = new Library();
        lib.displayLibraryState();
        Reduction red = new Reduction(lib);
        red.reduceByConcern(visu.getConcern().toString());
        if(red.getNumberOfSuitableWidgets()==1){ // there is only one configuration available after this reduction
            System.out.println("There is only one widget suitable for your visualization requirements. Let's generate it.");
            visu.setWidgetName(red.getWidgetName().replace(" ",""));

            //Generation of the HTML code from the model
            String code = codeGeneration(dashboard);

            //Creation of the /product folder if it doesn't exist already
            FileOperation.setUpFolder(Consts.GENERATED_TARGET_FOLDER);

            //store the resulting visualization in a file named after the used concern
            FileOperation.fillFileFromObject(code, Consts.RUNTIME_FOLDER+Consts.GENERATED_TARGET_FOLDER+concern.toString() + ".html");
        }
    }

}
