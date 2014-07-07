package EntryPoint;

import exception.BadIDException;
import exception.FMEngineException;
import exception.GetNameOnNonCompleteConfiguration;
import fr.familiar.interpreter.VariableNotExistingException;
import fr.familiar.parser.VariableAmbigousConflictException;
import fr.familiar.variable.FeatureVariable;
import fr.familiar.variable.Variable;
import kernel.Pilot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan Logre on 02/07/2014.
 */
public class Reduction {
    // the singleton instance of the pilot
    private static final Pilot pilot = Pilot.getInstance();

    // the unique ID of the configuration representing the current reduction of the library
    private String configId;
    private Library library;

    public Reduction(Library library){
       this.library = library;
       this.configId = pilot.newConfig(library.getId());
    }

    /*
     * This function take a list of features to select on a given configuration
     */
    public void reduceByConcerns(List<String> ls){
        for(String s : ls)
            reduceByConcern(s);
    }

    /*
     * This function take a feature to select on a given configuration
     */
    public void reduceByConcern(String s){
        try {
            System.out.println("select " + s + " in " + configId);
            pilot.eval("select " + s + " in " + configId);
            //TODO handle the eval in the pilot
        } catch (FMEngineException e) {
            e.printStackTrace();
        }
    }

    public int getNumberOfSuitableWidgets() throws BadIDException {
        try {
            String subLibrary = pilot.asFM(configId);
            FeatureVariable f_var = pilot.getFVariable(subLibrary+".Name");
            return f_var.children().getVars().size();
        } catch (VariableNotExistingException | VariableAmbigousConflictException e) {
            throw new BadIDException("The ID " + this.configId + " appears to be incorrect.");
        }
    }

    public String getWidgetName() throws GetNameOnNonCompleteConfiguration, BadIDException {
        FeatureVariable f_var;
        try{
            String subLibrary = pilot.asFM(configId);
            f_var = pilot.getFVariable(subLibrary+".Name");
        } catch (VariableNotExistingException | VariableAmbigousConflictException e) {
            throw new BadIDException("The ID " + this.configId + " appears to be incorrect.");
        }
        if(f_var.children().getVars().size()==1){
            Variable[] varSet = new Variable[]{};
            Variable v = f_var.children().getVars().toArray(varSet)[0];
            return v.getValue();
        }
        else throw new GetNameOnNonCompleteConfiguration("Reduction "+configId+" appears not to be complete.");
    }

    public List<String> getWidgetsNames() throws BadIDException {
        List<String> res = new ArrayList<>();
        FeatureVariable f_var;
        try{
            String subLibrary = pilot.asFM(configId);
            f_var = pilot.getFVariable(subLibrary+".Name");
        } catch (VariableNotExistingException | VariableAmbigousConflictException e) {
            throw new BadIDException("The ID " + this.configId + " appears to be incorrect.");
        }
        for(Variable v : f_var.children().getVars()){
            res.add(v.getIdentifier());
        }

        return res;
    }
}
