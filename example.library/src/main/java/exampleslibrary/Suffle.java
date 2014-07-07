package exampleslibrary;

import EntryPoint.Library;
import EntryPoint.Reduction;
import constants.Consts;
import exception.BadIDException;
import exception.GetNameOnNonCompleteConfiguration;
import metaclasses.*;
import utils.FileOperation;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

import static model.exploitation.CodeGeneration.codeGeneration;

/**
 * Created by ivan on 04/07/2014.
 */
public class Suffle {

    public static void main(String[] args) throws IOException, GetNameOnNonCompleteConfiguration, BadIDException {

        //Design the model of the wanted dashboard
        Dashboard dashboard = new Dashboard();

        Random random = new Random();

        for(int i=0;i<5;i++) {

            Visualization visu = new Visualization();
            Data data = new Data(Consts.getRandomSenMLData(), Format.SenML);
            Concern concern = Concern.values()[random.nextInt(Integer.MAX_VALUE)%2];
            visu.addData(data);
            visu.addConcern(concern);
            dashboard.addVisualization(visu);

            //Use feature model to find a suitable generable widget
            Library lib = new Library();
            //lib.displayLibraryState();
            Reduction red = new Reduction(lib);
            red.reduceByConcern(visu.getConcern().toString());
            visu.setWidgetName(red.getWidgetName().replace(" ", ""));
        }

        //Generation of the HTML code from the model
        String code = codeGeneration(dashboard);

        //Creation of the /product folder if it doesn't exist already
        FileOperation.setUpFolder(Consts.GENERATED_TARGET_FOLDER);

        //store the resulting visualization in a file named after the used concern
        FileOperation.fillFileFromObject(code, Consts.RUNTIME_FOLDER+ Consts.GENERATED_TARGET_FOLDER + "suffle.html");
    }
}
