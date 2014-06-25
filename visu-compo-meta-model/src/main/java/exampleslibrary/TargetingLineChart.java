package exampleslibrary;

import metaclasses.Concern;
import metaclasses.Data;
import metaclasses.Visualization;
import utils.FileOperation;

import java.io.File;
import java.nio.file.Paths;

import static utils.CodeGeneration.codeGeneration;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
public class TargetingLineChart {

    /*
     * This example means to illustrate the capability of designing single visualization dashboard with continuous data
     * It has to :
     *  - instantiate a valid model (conforms to the meta model)
     *  - reduce a configuration of the feature model according to the "continuous" criteria
     *  - generate the code of the resulting visualization
     */
    public static void main(String[] args) {

        //Design the model of the wanted dashboard
        Data data = new Data("http://users.polytech.unice.fr/~logre/resources/temp2.senml");
        Concern concern = Concern.continuous;
        Visualization visu = new Visualization(data, concern);

        //Generation of the HTML code from the model
        String code = codeGeneration(visu);

        //Creation of the /product folder if it doesn't exist already
        File f = new File(Paths.get("").toAbsolutePath().toString()+"/visu-compo-meta-model/products/");
        if((!f.exists())||(!f.isDirectory())){f.mkdirs();}

        //store the resulting visualization in a file named after the used concern
        FileOperation.fillFileFromObject(code, Paths.get("").toAbsolutePath().toString() + "/visu-compo-meta-model/products/" + concern.toString() + ".html");
    }

}
