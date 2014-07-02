package EntryPoint;

import exception.FMEngineException;
import fr.unice.polytech.modalis.familiar.interpreter.VariableNotExistingException;
import fr.unice.polytech.modalis.familiar.parser.VariableAmbigousConflictException;
import fr.unice.polytech.modalis.familiar.variable.FeatureModelVariable;
import fr.unice.polytech.modalis.familiar.variable.FeatureVariable;
import fr.unice.polytech.modalis.familiar.variable.Variable;
import kernel.Pilot;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

/**
 * Created by Ivan Logre on 26/06/2014.
 */
   public class FMExposer {

    // the singleton instance of the pilot
    private static final Pilot pilot = Pilot.getInstance();
    // the unique ID of the feature model representing the variability of known widgets
    private String fm_id;

    // default constructor using the default path for the fms declaration file
    public FMExposer(){
        new FMExposer(Paths.get("").toAbsolutePath().toString()+"/familiar-pilot/src/main/resources/"+"fms_min.fml");
    }

    //TODO illustrate the capability to merge a given FMs file calling this function
    public FMExposer(String FM_file_path){
        try {
            // test the existence of the file
            if (!new File(FM_file_path).exists())
                throw new IOException("The given path does not exist !");

            // launch the evaluation on the file, line by line. it should declare the "atomic" features models (products)
            pilot.evalFile(FM_file_path);

            fm_id = newID("fm_");

            List<String> fm_names = extractFeatureModelsNames(FM_file_path);

            StringBuilder command = new StringBuilder(" = merge sunion {");
            for(String s : fm_names){
                command.append(s);
                command.append(" ");
            }
            command.append("}");
            pilot.eval(fm_id + command);

            FeatureModelVariable fm_var = pilot.getFMVariable(fm_id);
            System.out.println("Feature models merge result :");
            System.out.println(fm_var.toString());
            System.out.println("Number of possible configuration :");
            System.out.println(fm_var.counting());
            System.out.println("__________________________________");


        } catch (FMEngineException | IOException | VariableNotExistingException | VariableAmbigousConflictException e) {
            e.printStackTrace();
        }
    }

    private List<String> extractFeatureModelsNames(String fm_file_path) {
        List<String> res = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fm_file_path)));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.substring(0, line.indexOf("=") - 1);
                res.add(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public FMExposer mergeWithFeatureModelFromPath(String FM_file_path){
        try {
            // test the existence of the file
            if (!new File(FM_file_path).exists())
                throw new IOException("The given path does not exist !");

            // launch the evaluation on the file, line by line. it should declare the "atomic" features models (products)
            pilot.evalFile(FM_file_path);

            String fm_id_bis = newID("fm_");
            System.out.println(fm_id_bis  + " = merge sunion fm*");
            pilot.eval(fm_id_bis  + " = merge sunion fm*");

            fm_id = fm_id_bis;

        } catch (FMEngineException | IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    //TODO fme.fm_id
    public FMExposer merge(FMExposer fme){
        String fm_id_bis = newID("fm_");
        try {
            pilot.eval(fm_id_bis  + " = merge sunion fm*");
        } catch (FMEngineException e) {
            e.printStackTrace();
        }
        fm_id = fm_id_bis;
        return this;
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
        } catch (FMEngineException | VariableAmbigousConflictException | VariableNotExistingException e) {
            e.printStackTrace();
        }
        return -1;
    }


    private String newID(String prefix){
        return prefix+randomUUID().toString().replace("-","");
    }

    public String getWidgetName(String config_id) {
        try{
            pilot.eval("fm_temp = asFM " + config_id);

            FeatureVariable f_var = pilot.getFVariable("fm_temp.Name");

            if(f_var.children().getVars().size()==1){
                //System.out.println("Value : "+v.getValue());
                Variable[] varSet = new Variable[]{};
                Variable v = f_var.children().getVars().toArray(varSet)[0];
                return v.getValue();
            }
            else throw new Exception("Don't understand what is going on! Should always be only one child to Name if the configuration is minimal...");
//todo est-ce que ce cas arrive ? Si oui : design a test. Si non : virer le if.

        } catch (VariableNotExistingException | VariableAmbigousConflictException | FMEngineException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }





    public static void main(String[] args) {
        FMExposer exposer = new FMExposer();
        String config = exposer.newConfig();
        double left = exposer.reduceByConcern("Continuous",config);


        if(left==1){ // there is only one configuration available
            String widgetName = exposer.getWidgetName(config);
            System.out.println("Final widget = "+widgetName);
        }
    }


}
