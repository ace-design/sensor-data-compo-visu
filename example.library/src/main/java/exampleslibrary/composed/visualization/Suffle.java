package exampleslibrary.composed.visualization;

import EntryPoint.Universe;
import constants.Consts;
import exception.*;
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

    public static void main(String[] args) throws IOException, GetUniqueElementOnNonCompleteConfiguration, BadIDException, UnhandledDataFormatException, UnhandledFamiliarException, ReductionException, EmptyUniverseException {

        Random random = new Random();
        List<String> datasets = new ArrayList<>();
        datasets.add(Consts.ALTITUDE_SENML);
        datasets.add(Consts.TEMP_NEG_SENML);
        datasets.add(Consts.SPEED_SENML);
        datasets.add(Consts.TEMP_SENML);

         /////
        //1//  Design the model of the wanted dashboard
       /////
        Dashboard dashboard = new Dashboard();

        for(int i=0;i<4;i++) {
            Visualization visu = new Visualization();
            String rand;
            do rand = Consts.getRandomSenMLData(); while(!datasets.contains(rand));
            datasets.remove(rand);
            Data data = new Data(rand, Format.SenML);
            System.out.println("Data "+i+" = "+data.getUrl());
            visu.addData(data);
            dashboard.addVisualization(visu);
            visu.addConcern(Concern.Extremum);


            /////
            //2//  Use feature model to find a suitable generable widget
           /////
            Universe univ = new Universe();
            univ.displayUniverseState();
            univ.reduceByConcerns(visu.getConcernNames());
            while (!univ.isMinimal()) {
                Concern concern = Concern.values()[random.nextInt(Integer.MAX_VALUE) % 2];
                System.out.println("Data "+i+", concern added : "+concern);
                visu.addConcern(concern);
                univ.reduceByConcern(concern.name());
            }
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
        FileOperation.fillFileFromObject(code, Consts.RUNTIME_FOLDER+ Consts.GENERATED_TARGET_FOLDER + "suffle.html");
    }
}
