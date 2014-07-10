package kernel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import exception.FMEngineException;
import fr.familiar.variable.*;
import org.apache.log4j.Logger;

import fr.familiar.interpreter.*;
import fr.familiar.interpreter.FMLShell;
import fr.familiar.interpreter.VariableNotExistingException;
import fr.familiar.parser.FMLCommandInterpreter;
import fr.familiar.parser.VariableAmbigousConflictException;
import utils.ToolBox;

import static org.junit.Assert.*;

/**
 * Created by Simon Urli.
 * Edited by Ivan Logre
 */
public class Pilot {

    private final FMLShell _shell;
    private final FMLCommandInterpreter _environment;
    private boolean hasBeenParsed;
    private static final Logger log = Logger.getLogger(Pilot.class);
    private static Pilot instance;

    private Pilot() {
        //if (FMLShell.getInstance() == null)
        _shell = FMLShell.instantiateStandalone(System.in);
        //else
        //	_shell = FMLShell.getInstance();
        _environment = _shell.getCurrentEnv();
        _shell.setVerbose(false);
        log.debug("Environment du shell :"+_environment);
        hasBeenParsed = false;
    }

    public static Pilot getInstance() {
        if(instance==null)
            instance = new Pilot();
        instance.getShell().printFMLHeader();
        return instance;
    }

    /**
     * Reset the environment
     */
    public void clearInterpreter() {
        this._shell.reset();
    }

    /**
     * Eval list of fm one by one
     * @param inlineFMs the list of fms to be evaluated
     * @throws FMEngineException, FileNotFoundException, IOException
     */
    public List<String> declareFMs(List<String> inlineFMs) throws FMEngineException, IOException {
        List<String> FM_IDs = new ArrayList<>();
        StringBuilder t = new StringBuilder();
        String idTemp;
        for(String fm : inlineFMs){
            idTemp = ToolBox.newID("fm_");
            FM_IDs.add(idTemp);
            t.append(idTemp);
            t.append(" = ");
            t.append(fm);
            t.append(" ");
        }
        this.eval(t.toString());
        return FM_IDs;
    }

    public String declareFM(String formula) throws FMEngineException {
        StringBuilder t = new StringBuilder();
        String idFM;
        idFM = ToolBox.newID("fm_");
        t.append(idFM);
        t.append(" = ");
        t.append(formula);
        this.eval(t.toString());
        return idFM;
    }

    /**
     * Extract the FMs declaration from a file
     * @param filename the path of the .fml file to be evaluated
     * @throws FMEngineException, IOException
     */
    public List<String> extractFMsByFile(String filename) throws IOException {
        List<String> inlineFMs = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        while (br.ready()) {
            inlineFMs.add(br.readLine());
        }
        return inlineFMs;
    }


    public String merge(List<String> knownFMs){
        String fm_id = ToolBox.newID("fm_");
        StringBuilder command = new StringBuilder();
        command.append(fm_id);
        command.append(" = merge sunion {");
        for(String s : knownFMs) {
            command.append(s);
            command.append(" ");
        }
        command.append("}");
        try {
            this.eval(command.toString());
        } catch (FMEngineException e) {
            e.printStackTrace();
        }
        return fm_id;
    }

    public String asFM(String configID){
        String fm_id = ToolBox.newID("fm_");
        try {
            this.eval(fm_id+" = asFM " + configID);
        } catch (FMEngineException e) {
            e.printStackTrace();
        }
        return fm_id;
    }


    /**
     * @param instr
     *            the FAMILIAR expressions to eval
     * @throws FMLFatalError
     *             in case there is a fatal error during the evaluation
     * @throws FMLAssertionError
     *             in case there is an assertion (using assert in FAMILIAR)
     *             error during the evaluation
     */
    public void eval(String instr) throws FMEngineException {
        log.debug(instr);
        _shell.parse(instr);

        if (_shell.hasFatalErrors()) {
            //throw new FMLFatalError(_shell.getFatalErrors());
            throw new FMEngineException("Fatal error :"+_shell.getFatalErrors().toString());
        }

        if (_shell.hasAssertionErrors()) {
            hasBeenParsed = true; // considering that assertion does not break
            // the parsing process
            //throw new FMLAssertionError(_shell.getAssertionErrors());
            throw new FMEngineException("Assertion error :"+_shell.getFatalErrors().toString());
        }
    }

    /**
     * @param id
     *            identifier of a variable in the environment
     * @return variable 'id' in the environment
     * @throws VariableNotExistingException
     *             in case there is no variable associated to the identifier
     * @throws VariableAmbigousConflictException
     *             in case there is no explicit identifier (ambiguity)
     */
    public Variable getVariable(String id) {
        try {
            return (VariableImpl) _environment.getVariable(id);
        } catch (VariableNotExistingException | VariableAmbigousConflictException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String newConfig(String fmID){
        String config_id = ToolBox.newID("c_");
        try {
            this.eval(config_id + " = configuration " + fmID);
        } catch (FMEngineException e) {
            e.printStackTrace();
        }
        return config_id;
    }

    /**
     * @param id identifier of a FeatureModel variable
     * @return a variable of type feature model whose identifier is id in the
     *         current environment
     * @throws VariableAmbigousConflictException
     * @throws VariableNotExistingException
     */
    public FeatureModelVariable getFMVariable(String id)
            throws VariableNotExistingException,
            VariableAmbigousConflictException {
        Variable v = _environment.getVariable(id);
        assertNotNull(v);
        assertTrue(v instanceof FeatureModelVariable);
        return (FeatureModelVariable) v;

    }



    /**
     * @param id identifier of a Feature variable
     * @return a variable of type feature whose identifier is id in the
     *         current environment
     * @throws VariableAmbigousConflictException
     * @throws VariableNotExistingException
     */
    public FeatureVariable getFVariable(String id)
            throws VariableNotExistingException,
            VariableAmbigousConflictException {
        Variable v = _environment.getVariable(id);
        assertNotNull(v);
        assertTrue(v instanceof FeatureVariable);
        return (FeatureVariable) v;

    }

    /**
     *
     * @param id identifier of a Configuration variable
     * @return
     * @throws VariableNotExistingException
     * @throws VariableAmbigousConflictException
     */
    ConfigurationVariable getConfigurationVariable(String id)
            throws VariableNotExistingException,
            VariableAmbigousConflictException {
        Variable v = _environment.getVariable(id);
        assertNotNull(v);
        assertTrue(v instanceof ConfigurationVariable);
        return (ConfigurationVariable) v;
    }


    /**
     * @param id identifier of a Set variable
     * @return a variable of type set whose identifier is id in the current
     *         environment
     * @throws VariableAmbigousConflictException
     * @throws VariableNotExistingException
     */
    public SetVariable getSetVariable(String id)
            throws VariableNotExistingException,
            VariableAmbigousConflictException {
        Variable v = _environment.getVariable(id);
        assertNotNull(v);
        assertTrue(v instanceof SetVariable);
        return (SetVariable) v;

    }

    /**
     *
     * @return the internal shell
     */
    FMLShell getShell() {
        return _shell;
    }

    /**
     * Set the verbosity of familiar
     * @param b
     */
    public void setVerbose(boolean b) {
        _shell.setVerbose(b);
    }

    /**
     * Get the collection of selected features names
     * @param configurationID the identifier of the configuration to check
     * @return a collection of feature name
     * @throws FMEngineException
     */
    public Collection<String> getSelectedFeature(String configurationID)
            throws FMEngineException {
        try {
            ConfigurationVariable cv = this.getConfigurationVariable(configurationID);
            this.eval("selectedF "+configurationID);
            return cv.getSelected();
        } catch (Exception e) {
            throw new FMEngineException(e.getMessage());
            //e.printStackTrace();
        }
    }

    /**
     * Get the collection of deselected features names
     * @param configurationID the identifier of the configuration to check
     * @return a collection of feature name
     * @throws FMEngineException
     */
    public Collection<String> getDeselectedFeature(String configurationID)
            throws FMEngineException {
        try {
            ConfigurationVariable cv = this.getConfigurationVariable(configurationID);
            this.eval("deselectedF "+configurationID);
            return cv.getDeselected();
        } catch (Exception e) {
            throw new FMEngineException(e.getMessage());
            //e.printStackTrace();
        }
    }

    /**
     * Get the collection of unselected features names
     * @param configurationID the identifier of the configuration to check
     * @return a collection of feature name
     * @throws FMEngineException
     */
    public Collection<String> getUnselectedFeature(String configurationID) throws FMEngineException {
        try {
            ConfigurationVariable cv = this.getConfigurationVariable(configurationID);
            this.eval("unselectedF "+configurationID);
            return cv.getUnselected();
        } catch (Exception e) {
            throw new FMEngineException(e.getMessage());
            //e.printStackTrace();
        }
    }

    /**
     * Check if a configuration is complete
     * @param configurationID the identifier of a configuration to check
     * @return Boolean : true if the configuration is complete, false if not.
     * @throws FMEngineException
     */
    public boolean isComplete(String configurationID) throws FMEngineException {
        try {
            ConfigurationVariable cv = this.getConfigurationVariable(configurationID);
            return cv.isComplete();
        } catch (Exception e) {
            throw new FMEngineException(e.getMessage());
        }
    }

    public double countingOnFM(String FMID) throws VariableNotExistingException, VariableAmbigousConflictException {
        return getFMVariable(FMID).counting();
    }


    public void selectFeatureOnConfiguration(String concern, String configId) throws FMEngineException {
        this.eval("select " + concern + " in " + configId);
    }
}
