package EntryPoint;

import exception.FMEngineException;
import fr.unice.polytech.modalis.familiar.interpreter.VariableNotExistingException;
import fr.unice.polytech.modalis.familiar.parser.VariableAmbigousConflictException;
import fr.unice.polytech.modalis.familiar.variable.FeatureModelVariable;
import kernel.Pilot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by Ivan Logre on 26/06/2014.
 */
public class FMExposer {
    public static void main(String[] args) {

        //get the singleton instance of the pilot
        Pilot pilot = Pilot.getInstance();

        // set the path of the file containing the feature model declaration by default
        String FM_file_path = Paths.get("").toAbsolutePath().toString()+"/familiar-pilot/src/main/resources/"+"fms_functions.fml";
        // if the program is launch with a parameter, use this path
        if (args.length>0)
            FM_file_path = args[0];
        try {
            // test the existence of the file
            if (!new File(FM_file_path).exists())
                throw new IOException("The given path does not exist !");

            // launch the evaluation on the file, line by line. it should declare the "atomic" features models (products)
            pilot.evalFile(FM_file_path);
            // merge those atomic fms
            //pilot.eval("fm_merged = merge sunion fm*");
            FeatureModelVariable fm_var = pilot.getFMVariable("fm_merged");
            System.out.println("Feature models merge result :");
            System.out.println(fm_var.toString());
            System.out.println("Number of possible configuration :");
            System.out.println(fm_var.counting());



            pilot.eval("c = configuration fm_merge");


        } catch (FMEngineException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (VariableNotExistingException e) {
            e.printStackTrace();
        } catch (VariableAmbigousConflictException e) {
            e.printStackTrace();
        }
    }


}
