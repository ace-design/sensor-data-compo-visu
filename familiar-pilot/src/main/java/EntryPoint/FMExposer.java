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

import static java.util.UUID.randomUUID;

/**
 * Created by Ivan Logre on 26/06/2014.
 */
   public class FMExposer {


    // the singleton instance of the pilot
    private static Pilot pilot = Pilot.getInstance();
    // the unique ID of the feature model representing the variability of known widgets
    private static String fm_id;

    // default constructor using the default path for the fms declaration file
    public FMExposer(){
        new FMExposer(Paths.get("").toAbsolutePath().toString()+"/familiar-pilot/src/main/resources/"+"fms_min.fml");
    }

    public FMExposer(String FM_file_path){
        try {
            // test the existence of the file
            if (!new File(FM_file_path).exists())
                throw new IOException("The given path does not exist !");

            // launch the evaluation on the file, line by line. it should declare the "atomic" features models (products)
            pilot.evalFile(FM_file_path);

            fm_id = newID("fm_");
            System.out.println(fm_id + " = merge sunion fm*");
            pilot.eval(fm_id + " = merge sunion fm*");

            FeatureModelVariable fm_var = pilot.getFMVariable(fm_id);
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

    public String newConfig(){
        String config_id = newID("c_");
        try {
            System.out.println(config_id + " = configuration " + fm_id);
            pilot.eval(config_id + " = configuration " + fm_id);
        } catch (FMEngineException e) {
            e.printStackTrace();
        }
        return config_id;
    }

    /*
     * This function take a list of features to select on a given configuration
     * it return the number of possible configuration left.
     */
    public double reduceByConcerns(List<String> ls, String config_id){
        double res = 0;
        for(String s : ls)
            res += reduceByConcern(s,config_id);

        System.out.println("Is complete (config "+config_id+") :");
        try {
            pilot.isComplete(config_id);
        } catch (FMEngineException e) {
            e.printStackTrace();
        }

        return res;
    }

    /*
     * This function take a feature to select on a given configuration
     * it return the number of possible configuration left.
     */
    public double reduceByConcern(String s, String config_id){
        try {
            System.out.println("select " + s + " in " + config_id);
            pilot.eval("select " + s + " in " + config_id);
            System.out.println("fm_temp = asFM " + config_id);
            pilot.eval("fm_temp = asFM " + config_id);
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

    public static void main(String[] args) {
        FMExposer exposer = new FMExposer();
        String config = exposer.newConfig();
        double left = exposer.reduceByConcern("Continuous",config);
        System.out.println();
        System.out.println("Final result : "+left);
    }

    private String newID(String prefix){
        return prefix+randomUUID().toString().replace("-","");
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
