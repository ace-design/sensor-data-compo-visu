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
import java.util.Scanner;

import static model.exploitation.CodeGeneration.codeGeneration;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
class GenericTargeting {

    /*
     * This example means to illustrate the capability of designing single visualization dashboard
     * Letting the user choose the Concern
     * It has to :
     *  - instantiate a valid model (conforms to the meta model)
     *  - reduce a configuration of the feature model according to the chosen criteria
     *  - generate the code of the resulting visualization
     */
    public static void main(String[] args) throws IOException, BadIDException, GetUniqueElementOnNonCompleteConfiguration, UnhandledDataFormatException {

        //Design the model of the wanted dashboard
        Dashboard dashboard = new Dashboard();
        Data data = new Data(Consts.TEMP_SENML, Format.SenML);
        Visualization visu = new Visualization(data);
        dashboard.addVisualization(visu);
        System.out.println("What Concern do you want your visualization to fulfill ?");

        Boolean validEntry = false;
        String entry;
        do {
            System.out.println("Available Concerns are :");
            for (Concern c : Concern.values())
                System.out.println("     - "+c.toString());
            Scanner scan = new Scanner(System.in);

            entry = scan.nextLine();
            for (Concern c : Concern.values()){
                if(c.toString().equals(entry)) {
                    validEntry = true;
                    break;
                }
            }
        }while(!validEntry);
        visu.addConcern(Concern.valueOf(entry));

        System.out.println("Concern "+entry+" added to your visualization.");
        System.out.println("Let see what we got for you...");


        //Use feature model to find a suitable generable widget
        Library lib = new Library();
        lib.displayLibraryState();
        Reduction red = new Reduction(lib);
        red.reduceByConcerns(visu.getConcernNames());
        if(red.getNumberOfSuitableWidgets()==1){ // there is only one configuration available after this reduction
            System.out.println("There is only one widget suitable for your visualization requirements. Let's generate it.");
            visu.setWidgetName(red.getWidgetName().replace(" ",""));
            visu.setLibraryName(red.getLibraryName());

            //Generation of the HTML code from the model
            String code = codeGeneration(dashboard);


            //Creation of the /product folder if it doesn't exist already
            FileOperation.setUpFolder(Consts.GENERATED_TARGET_FOLDER);

            //store the resulting visualization in a file named after the used concern
            FileOperation.fillFileFromObject(code, Consts.RUNTIME_FOLDER+Consts.GENERATED_TARGET_FOLDER + entry + ".html");
            System.out.println("OK ! Go find your html visualization in the product folder.");
        }
        else System.out.println("There more than one widget suitable for your visualization requirements. It's a TODO !");

    }


}
