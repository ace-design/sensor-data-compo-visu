package exampleslibrary;

import EntryPoint.Library;
import EntryPoint.Reduction;
import constants.Consts;
import exception.BadIDException;
import exception.GetUniqueElementOnNonCompleteConfiguration;
import exception.UnhandledDataFormatException;
import metaclasses.*;
import utils.FileOperation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static model.exploitation.CodeGeneration.codeGeneration;

/**
 * Created by ivan on 04/07/2014.
 */
public class Suffle {

    public static void main(String[] args) throws IOException, GetUniqueElementOnNonCompleteConfiguration, BadIDException, UnhandledDataFormatException {

        //Design the model of the wanted dashboard
        Dashboard dashboard = new Dashboard();

        Random random = new Random();

        List<String> datasets = new ArrayList<>();
        datasets.add(Consts.ALTITUDE_SENML);
        datasets.add(Consts.TEMP_NEG_SENML);
        datasets.add(Consts.SPEED_SENML);
        datasets.add(Consts.TEMP_SENML);

        for(int i=0;i<4;i++) {
            Visualization visu = new Visualization();
            String rand;
            do rand = Consts.getRandomSenMLData(); while(!datasets.contains(rand));
            datasets.remove(rand);
            Data data = new Data(rand, Format.SenML);
            System.out.println("Data "+i+" = "+data.getUrl());
            visu.addData(data);
            dashboard.addVisualization(visu);

            //Use feature model to find a suitable generable widget
            Library lib = new Library();
            //lib.displayLibraryState();
            Reduction red = new Reduction(lib);
            visu.addConcern(Concern.Extremum);
            red.reduceByConcern(Concern.Extremum.toString());
            while(!red.isMinimal()) {
                Concern concern = Concern.values()[random.nextInt(Integer.MAX_VALUE) % 2];
                System.out.println("Data "+i+", concer added : "+concern);
                visu.addConcern(concern);
                red.reduceByConcern(concern.toString());
            }
            visu.setWidgetName(red.getWidgetName().replace(" ", ""));
            visu.setLibraryName(red.getLibraryName());
        }

        //Generation of the HTML code from the model
        String code = codeGeneration(dashboard);

        //Creation of the /product folder if it doesn't exist already
        FileOperation.setUpFolder(Consts.GENERATED_TARGET_FOLDER);

        //store the resulting visualization in a file named after the used concern
        FileOperation.fillFileFromObject(code, Consts.RUNTIME_FOLDER+ Consts.GENERATED_TARGET_FOLDER + "suffle.html");
    }
}
