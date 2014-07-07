package EntryPoint;

import exception.BadIDException;
import exception.FMEngineException;
import fr.familiar.interpreter.VariableNotExistingException;
import fr.familiar.parser.VariableAmbigousConflictException;
import fr.familiar.variable.FeatureModelVariable;
import fr.familiar.variable.FeatureVariable;
import fr.familiar.variable.Variable;
import kernel.Pilot;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan Logre on 02/07/2014.
 */
public class Library {
    // the singleton instance of the pilot
    private static final Pilot pilot = Pilot.getInstance();

    // the unique ID of the feature model representing the variability of known widgets in this Library
    private String fm_id;

    // default constructor using the default path for the fms declaration file
    public Library() throws IOException {
        this(Paths.get("").toAbsolutePath().toString()+"/familiar-pilot/src/main/resources/"+"fms_min.fml");
    }

    public Library(String libraryPath) throws IOException {
        try {
            // test the existence of the file
            if (!new File(libraryPath).exists())
                throw new IOException("The given path does not exist !");

            // launch the evaluation on the file, line by line. it should declare the "atomic" features models (products)
            List<String> widgets = pilot.declareFMsByFile(libraryPath);

            this.fm_id = pilot.merge(widgets);

        } catch (FMEngineException e) {
            e.printStackTrace();
        }
    }

    public Library merge(Library l2){
        List<String> list = new ArrayList<>();
        list.add(this.fm_id);
        list.add(l2.fm_id);
        this.fm_id = pilot.merge(list);
        return this;
    }



    public Library mergeWithLibraryFileFromPath(String FM_file_path){
        try {
            // test the existence of the file
            if (!new File(FM_file_path).exists())
                throw new IOException("The given path does not exist !");

            // launch the evaluation on the file, line by line. it should declare the "atomic" features models (products)
            List<String> widgets = pilot.declareFMsByFile(FM_file_path);
            widgets.add(this.fm_id);
            this.fm_id = pilot.merge(widgets);

        } catch (FMEngineException | IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void displayLibraryState(){
        FeatureModelVariable fm_var = null;
        try {
            fm_var = pilot.getFMVariable(this.fm_id);
        } catch (VariableAmbigousConflictException | VariableNotExistingException e) {
            e.printStackTrace();
        }
        System.out.println("__________________________________");
        System.out.println("Feature models merge result :");
        System.out.println(fm_var.toString());
        System.out.println("Number of possible configuration :");
        System.out.println(fm_var.counting());
        System.out.println("__________________________________");
    }

    public String getId() {
        return this.fm_id;
    }

    public double getNumberValidConfiguration() throws BadIDException{
        try {
            return pilot.countingOnFM(this.fm_id);
        } catch (VariableNotExistingException | VariableAmbigousConflictException e) {
            throw new BadIDException("The ID " + this.fm_id + " appears to be incorrect.");
        }
    }

    public List<String> getWidgetsNames() throws BadIDException {
        List<String> res = new ArrayList<>();
        FeatureVariable f_var;
        try{
            f_var = pilot.getFVariable(this.getId()+".Name");
        } catch (VariableNotExistingException | VariableAmbigousConflictException e) {
            throw new BadIDException("The ID " + this.getId() + " appears to be incorrect.");
        }
        for(Variable v : f_var.children().getVars()){
            res.add(v.getIdentifier());
        }

        return res;
    }
}
