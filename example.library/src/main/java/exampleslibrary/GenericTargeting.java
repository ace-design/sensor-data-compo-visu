package exampleslibrary;

import EntryPoint.Library;
import EntryPoint.Reduction;
import metaclasses.Concern;
import metaclasses.Data;
import metaclasses.Format;
import metaclasses.Visualization;
import utils.FileOperation;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

import static model.exploitation.CodeGeneration.codeGeneration;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
class GenericTargeting {

    private static final String GENERATED_TARGET_FOLDER = "/example.library/products/";

    /*
     * This example means to illustrate the capability of designing single visualization dashboard
     * Letting the user choose the Concern
     * It has to :
     *  - instantiate a valid model (conforms to the meta model)
     *  - reduce a configuration of the feature model according to the chosen criteria
     *  - generate the code of the resulting visualization
     */
    public static void main(String[] args) throws IOException {

        //Design the model of the wanted dashboard
        Data data = new Data("http://users.polytech.unice.fr/~logre/resources/temp2.senml", Format.SenML);
        Visualization visu = new Visualization(data);

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
        red.reduceByConcern(visu.getConcern().toString());
        if(red.getNumberOfSuitableWidgets()==1){ // there is only one configuration available after this reduction
            System.out.println("There is only one widget suitable for your visualization requirements. Let's generate it.");
            visu.setWidgetName(red.getWidgetName().replace(" ",""));

            //Generation of the HTML code from the model
            String code = codeGeneration(visu);


            //Creation of the /product folder if it doesn't exist already
            FileOperation.setUpFolder(GENERATED_TARGET_FOLDER);

            //store the resulting visualization in a file named after the used concern
            FileOperation.fillFileFromObject(code, Paths.get("").toAbsolutePath().toString() + GENERATED_TARGET_FOLDER + entry + ".html");
            System.out.println("OK ! Go find your html visualization in the product folder.");
        }
    }


}
