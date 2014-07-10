package EntryPoint;

import exception.*;
import fr.familiar.interpreter.VariableNotExistingException;
import fr.familiar.parser.VariableAmbigousConflictException;
import fr.familiar.variable.FeatureModelVariable;
import fr.familiar.variable.FeatureVariable;
import fr.familiar.variable.Variable;
import kernel.Pilot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 10/07/2014.
 */
public class Universe {
    // the singleton instance of the pilot
    private static final Pilot pilot = Pilot.getInstance();

    // the unique ID of the feature model representing the variability of known widgets in this collection of widget
    private String ID;

    private List<Widget> widgets;

    public Universe() throws IOException, UnhandledFamiliarException {
        this(Universe.class.getClassLoader().getResource("fms_min.fml").getPath());
    }


    /* Launch the evaluation on the file widgetsFormulaPath, line by line.
     * It should declare the "atomic" features models (products)
     * we store their ID in familiar and their formula used to instantiate them in Widgets
     */
    public Universe(String widgetsFormulaPath) throws IOException, UnhandledFamiliarException {
        widgets = new ArrayList<>();
            // test the existence of the file
            if (!new File(widgetsFormulaPath).exists())
                throw new IOException("The given path -"+widgetsFormulaPath+"- does not exist !");
            List<String> inlineWidgets = pilot.extractFMsByFile(widgetsFormulaPath);
            for(String formula:inlineWidgets)
                widgets.add(new Widget(formula));
            this.ID = pilot.merge(getWidgetsIDs());
    }

    public static Universe merge(Universe u1, Universe u2){
        List<Widget> widgetList = new ArrayList<>();
        widgetList.addAll(u1.getWidgets());
        widgetList.addAll(u2.getWidgets());

        List<String> widgetIDList = new ArrayList<>();
        widgetIDList.addAll(u1.getWidgetsIDs());
        widgetIDList.addAll(u2.getWidgetsIDs());

        String newID = pilot.merge(widgetIDList);
        return new Universe(newID, widgetList);
    }


    public void displayUniverseState() throws BadIDException {
        FeatureModelVariable fm_var = null;
        try {
            fm_var = pilot.getFMVariable(this.ID);
        } catch (VariableAmbigousConflictException | VariableNotExistingException e) {
            throw new BadIDException("");
        }
        System.out.println("__________________________________");
        System.out.println("Feature models merge result :");
        System.out.println(fm_var.toString());
        System.out.println("Number of possible configuration :");
        System.out.println(fm_var.counting());
        System.out.println("__________________________________");
    }

    /*
     * This method return the number of asset still available so far.
     * If their is still a pie chart from AmChart and one from HighChart, their will be counted as two.
     */
    public double getNumberSuitableTargets() throws BadIDException{
        try {
            return pilot.countingOnFM(this.ID);
        } catch (VariableNotExistingException | VariableAmbigousConflictException e) {
            throw new BadIDException("The ID " + this.ID + " appears to be incorrect.");
        }
    }

    /*
     * This method return the number of widgets still available so far.
     * If their is still a pie chart from AmChart and one from HighChart, their will be counted as one.
     */
    public int getNumberOfSuitableWidgets() throws BadIDException {
        try {
            FeatureVariable f_var = pilot.getFVariable(ID+".Name");
            return f_var.children().getVars().size();
        } catch (VariableNotExistingException | VariableAmbigousConflictException e) {
            throw new BadIDException("The ID " + ID + " appears to be incorrect.");
        }
    }

    /*
     * This function take a list of features to select on a given configuration
     */
    public void reduceByConcerns(List<String> ls) throws ReductionException {
        for(String s : ls)
            reduceByConcern(s);
    }

    /*
     * This function take a feature to select on a given configuration
     */
    public void reduceByConcern(String concern) throws ReductionException {
        try {
            String configID = pilot.newConfig(ID);
            pilot.selectFeatureOnConfiguration(concern,configID);
            this.ID = pilot.asFM(configID);
        } catch (FMEngineException e) {
            throw new ReductionException("Something went bad during reduction by the concern " + concern + " on the Universe " + ID);
        }
    }

    /*
     * Returns the name of the last widget available, if there is only one.
     * Else : don't use this ! Use getWidgetsNames()
     */
    public String getLastWidgetName() throws GetUniqueElementOnNonCompleteConfiguration, BadIDException {
        FeatureVariable f_var;
        try{
            f_var = pilot.getFVariable(ID+".Name");
        } catch (VariableNotExistingException | VariableAmbigousConflictException e) {
            throw new BadIDException("The ID " + ID + " appears to be incorrect.");
        }
        if(f_var.children().getVars().size()==1){
            Variable[] varSet = new Variable[]{};
            Variable v = f_var.children().getVars().toArray(varSet)[0];
            return v.getValue();
        }
        else throw new GetUniqueElementOnNonCompleteConfiguration("Reduction "+ID+" appears not to be complete.");
    }

    /*
     * Returns the names of the widgets still available.
     */
    public List<String> getWidgetsNames() throws BadIDException {
        List<String> res = new ArrayList<>();
        FeatureVariable f_var;
        try{
            f_var = pilot.getFVariable(ID+".Name");
        } catch (VariableNotExistingException | VariableAmbigousConflictException e) {
            throw new BadIDException("The ID " + this.ID + " appears to be incorrect.");
        }
        for(Variable v : f_var.children().getVars())
            res.add(v.getIdentifier());
        return res;
    }

    /*
     * Returns the name of the last library available, if there is only one.
     * Else : don't use this ! Use getLibrariesNames()
     */
    public String getLastLibraryName() throws GetUniqueElementOnNonCompleteConfiguration, BadIDException {
        FeatureVariable f_var;
        try{
            f_var = pilot.getFVariable(ID+".Library");
        } catch (VariableNotExistingException | VariableAmbigousConflictException e) {
            throw new BadIDException("The ID " + this.ID + " appears to be incorrect.");
        }
        if(f_var.children().getVars().size()==1){
            Variable[] varSet = new Variable[]{};
            Variable v = f_var.children().getVars().toArray(varSet)[0];
            return v.getValue();
        }
        else throw new GetUniqueElementOnNonCompleteConfiguration("Reduction "+ID+" appears not to be complete.");
    }

    /*
     * Returns the names of the libraries still available.
     */
    public List<String> getLibrariesNames() throws GetUniqueElementOnNonCompleteConfiguration, BadIDException {
        List<String> res = new ArrayList<>();
        FeatureVariable f_var;
        try{
            f_var = pilot.getFVariable(ID+".Library");
        } catch (VariableNotExistingException | VariableAmbigousConflictException e) {
            throw new BadIDException("The ID " + this.ID + " appears to be incorrect.");
        }
        for(Variable v : f_var.children().getVars())
            res.add(v.getIdentifier());
        return res;
    }

    public boolean isMinimal() throws BadIDException{
        try {
            String configID = pilot.newConfig(ID);
            return pilot.isComplete(configID);
        } catch (FMEngineException e) {
            throw new BadIDException("The ID " + ID + " appears to be incorrect or can't have a configuration instantiated.");
        }
    }

    /* _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _   Getters & Setters  _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _*/

    public List<Widget> getWidgets() {
        return widgets;
    }

    public String getID() {
        return ID;
    }

    /* _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _Private useful methods _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _*/

    private List<String> getWidgetsIDs(){
        List<String> widgetsIDs = new ArrayList<>();
        for(Widget widget : widgets)
            widgetsIDs.add(widget.getWidgetID());
        return widgetsIDs;
    }


    private Universe(String ID, List<Widget> widgets){
        this.ID = ID;
        this.widgets = new ArrayList<>(widgets);
    }


}
