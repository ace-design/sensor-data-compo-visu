package EntryPoint;

import exception.FMEngineException;
import exception.GetNameOnNonCompleteConfiguration;
import fr.unice.polytech.modalis.familiar.interpreter.VariableNotExistingException;
import fr.unice.polytech.modalis.familiar.parser.VariableAmbigousConflictException;
import fr.unice.polytech.modalis.familiar.variable.FeatureVariable;
import fr.unice.polytech.modalis.familiar.variable.Variable;
import kernel.Pilot;

import java.util.List;

/**
 * Created by Ivan Logre on 02/07/2014.
 */
public class Reduction {
    // the singleton instance of the pilot
    private static final Pilot pilot = Pilot.getInstance();

    // the unique ID of the configuration representing the current reduction of the library
    private String configId;

    public Reduction(Library library){
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

    public int getNumberOfSuitableWidgets() {
        try {
            String subLibrary = pilot.asFM(configId);
            FeatureVariable f_var = pilot.getFVariable(subLibrary+".Name");
            return f_var.children().getVars().size();
        } catch (VariableNotExistingException | VariableAmbigousConflictException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getWidgetName() {
        try{
            String subLibrary = pilot.asFM(configId);
            FeatureVariable f_var = pilot.getFVariable(subLibrary+".Name");

            if(f_var.children().getVars().size()==1){
                Variable[] varSet = new Variable[]{};
                Variable v = f_var.children().getVars().toArray(varSet)[0];
                return v.getValue();
            }
            else throw new GetNameOnNonCompleteConfiguration();

        } catch (VariableNotExistingException | VariableAmbigousConflictException | GetNameOnNonCompleteConfiguration e) {
            e.printStackTrace();
        }
        return "";
    }
}
