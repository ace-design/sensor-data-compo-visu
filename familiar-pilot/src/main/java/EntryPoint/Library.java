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
    private List<String> atomicFMs;

    // default constructor using the default path for the fms declaration file
    public Library() throws IOException {
        this(Paths.get("").toAbsolutePath().toString()+"/familiar-pilot/src/main/resources/"+"fms_min.fml");
    }

    public Library(String libraryPath) throws IOException {
        atomicFMs = new ArrayList<>();
        try {
            // test the existence of the file
            if (!new File(libraryPath).exists())
                throw new IOException("The given path does not exist !");

            // launch the evaluation on the file, line by line. it should declare the "atomic" features models (products)
            atomicFMs = pilot.extractFMsByFile(libraryPath);
            pilot.declareFMs(atomicFMs);

            this.fm_id = pilot.merge(atomicFMs);

        } catch (FMEngineException e) {
            e.printStackTrace();
        }
    }

    private Library(String fm_id, List<String> atomicFMs){
        this.fm_id = fm_id;
        this.atomicFMs = new ArrayList<>(atomicFMs);
    }

    public static Library merge(Library l1, Library l2){
        List<String> list = new ArrayList<>();
        list.addAll(l1.getAtomicFMs());
        list.addAll(l2.getAtomicFMs());
        String new_fm_id = pilot.merge(list);
        return new Library(new_fm_id, list);
    }



    public void addWidgetsInLibraryFromFile(String FM_file_path){
        try {
            // test the existence of the file
            if (!new File(FM_file_path).exists())
                throw new IOException("The given path does not exist !");

            // launch the evaluation on the file, line by line. it should declare the "atomic" features models (products)
            List<String> widgets = pilot.extractFMsByFile(FM_file_path);
            this.atomicFMs.addAll(widgets);
            this.fm_id = pilot.merge(atomicFMs);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public List<String> getAtomicFMs() {
        return atomicFMs;
    }
}
