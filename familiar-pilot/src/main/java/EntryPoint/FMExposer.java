package EntryPoint;

import exception.FMEngineException;
import fr.unice.polytech.modalis.familiar.interpreter.VariableNotExistingException;
import fr.unice.polytech.modalis.familiar.parser.VariableAmbigousConflictException;
import fr.unice.polytech.modalis.familiar.variable.FeatureModelVariable;
import kernel.Pilot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

/**
 * Created by Ivan Logre on 26/06/2014.
 */
   public class FMExposer {


    // the singleton instance of the pilot
    private Pilot pilot = Pilot.getInstance();
    // the unique ID of the feature model representing the variability of known widgets
    private UUID fm_id;

    // default constructor using the default path for the fms declaration file
    public void FMExposer(){
        FMExposer(Paths.get("").toAbsolutePath().toString()+"/familiar-pilot/src/main/resources/"+"fms_min.fml");
    }

    public void FMExposer(String FM_file_path){
        try {
            // test the existence of the file
            if (!new File(FM_file_path).exists())
                throw new IOException("The given path does not exist !");

            // launch the evaluation on the file, line by line. it should declare the "atomic" features models (products)
            pilot.evalFile(FM_file_path);

            fm_id = randomUUID();
            pilot.eval(fm_id.toString() + " = merge sunion fm*");

            FeatureModelVariable fm_var = pilot.getFMVariable(fm_id.toString());
            System.out.println("Feature models merge result :");
            System.out.println(fm_var.toString());
            System.out.println("Number of possible configuration :");
            System.out.println(fm_var.counting());


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

    public UUID getConfig(){
        UUID config_id = randomUUID();
        try {
            pilot.eval(config_id.toString() + " = configuration " + fm_id.toString());
        } catch (FMEngineException e) {
            e.printStackTrace();
        }
        return config_id;
    }

    /*
     * This function take a list of features to select on a given configuration
     * it return the number of possible configuration left.
     */
    public double reduceByConcerns(List<String> ls, UUID config_id){
        double res = 0;
        for(String s : ls)
            res += reduceByConcern(s,config_id);
        return res;
    }

    /*
     * This function take a feature to select on a given configuration
     * it return the number of possible configuration left.
     */
    public double reduceByConcern(String s, UUID config_id){
        try {
            pilot.eval("select " + s + " in " + config_id.toString());
            pilot.eval("fm_temp = asFM " + config_id.toString());
            FeatureModelVariable fm_var = pilot.getFMVariable("fm_temp");
            System.out.println("Feature models merge result :");
            System.out.println(fm_var.toString());
            System.out.println("Number of possible configuration :");
            System.out.println(fm_var.counting());
            return fm_var.counting();
        } catch (FMEngineException e) {
            e.printStackTrace();
        } catch (VariableNotExistingException e) {
            e.printStackTrace();
        } catch (VariableAmbigousConflictException e) {
            e.printStackTrace();
        }
        return -1;
    }


    /*public static void main(String[] args) {

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
    }*/

}
