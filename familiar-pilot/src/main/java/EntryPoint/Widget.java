package EntryPoint;

import exception.FMEngineException;
import exception.UnhandledFamiliarException;
import fr.familiar.interpreter.VariableNotExistingException;
import fr.familiar.parser.VariableAmbigousConflictException;
import fr.familiar.variable.FeatureVariable;
import fr.familiar.variable.Variable;
import kernel.Pilot;

/**
 * Created by ivan on 10/07/2014.
 */
public class Widget {
    // the singleton instance of the pilot
    private static final Pilot pilot = Pilot.getInstance();

    // the feature model's unique ID of the familiar representation of this Widget
    private String widgetID;
    private String formula;
    private String name;


    public Widget(String formula) throws UnhandledFamiliarException {
        this.formula=formula;
        FeatureVariable f_var;
        try {
            widgetID = pilot.declareFM(formula);
            f_var = pilot.getFVariable(widgetID +".Name");
        } catch (FMEngineException | VariableNotExistingException | VariableAmbigousConflictException e) {
            throw new UnhandledFamiliarException("Failing declaration of a widget feature model.");
        }
        name = f_var.children().getVars().toArray(new Variable[]{})[0].getValue();
    }

    public String getFormula() {
        return formula;
    }

    public String getWidgetID() {
        return widgetID;
    }

    public String getName() {
        return name;
    }
}
