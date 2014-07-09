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
 * Created by ivan on 03/07/2014.
 */
public class TargetingColumnLeftLineChart {



    /*
     * This example means to illustrate the capability of designing single visualization dashboard with Discrete data
     * It has to :
     *  - instantiate a valid model (conforms to the meta model)
     *  - reduce a configuration of the feature model according to the "Discrete" criteria
     *  - generate the code of the resulting visualization
     */
    public static void main(String[] args) throws IOException, GetNameOnNonCompleteConfiguration, BadIDException, UnhandledDataFormatException {

        //Design the model of the wanted dashboard
        Dashboard dashboard = new Dashboard();

        // #1 visualization : Discrete
        Visualization visu = new Visualization();
        Data data = new Data(Consts.TEMP_SENML, Format.SenML);
        Concern concern = Concern.Discrete;
        visu.addData(data);
        visu.addConcern(concern);
        dashboard.addVisualization(visu);

        // #2 visualization : Continuous
        Visualization visu2 = new Visualization();
        Data data2 = new Data(Consts.PRES_SENML, Format.SenML);
        Concern concern2 = Concern.Continuous;
        visu2.addData(data2);
        visu2.addConcern(concern2);
        dashboard.addVisualization(visu2);

        for(Visualization v : dashboard.getVisualizationList()) {
            //Use feature model to find a suitable generable widget
            Library lib = new Library();
            lib.displayLibraryState();
            Reduction red = new Reduction(lib);
            red.reduceByConcern(v.getConcern().toString());

            System.out.println("There is only one widget suitable for your visualization requirements. Let's generate it.");
            v.setWidgetName(red.getWidgetName().replace(" ", ""));
        }




        //Generation of the HTML code from the model
        String code = codeGeneration(dashboard);

        //Creation of the /product folder if it doesn't exist already
        FileOperation.setUpFolder(Consts.GENERATED_TARGET_FOLDER);

        //store the resulting visualization in a file named after the used concern
        FileOperation.fillFileFromObject(code, Consts.RUNTIME_FOLDER+Consts.GENERATED_TARGET_FOLDER + concern + concern2 + ".html");
    }

}
