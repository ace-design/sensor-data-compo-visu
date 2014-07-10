package EntryPoint;

import exception.FMEngineException;
import exception.UnhandledFamiliarException;
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


    public Widget(String formula) throws UnhandledFamiliarException {
        this.formula=formula;
        try {
            widgetID = pilot.declareFM(formula);
        } catch (FMEngineException e) {
            throw new UnhandledFamiliarException("Failing declaration of a widget feature model.");
        }
    }

    public String getFormula() {
        return formula;
    }

    public String getWidgetID() {
        return widgetID;
    }
}
