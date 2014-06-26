package kernel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import exception.FMEngineException;
import org.apache.log4j.Logger;

import fr.unice.polytech.modalis.familiar.interpreter.*;
import fr.unice.polytech.modalis.familiar.interpreter.FMLShell;
import fr.unice.polytech.modalis.familiar.interpreter.VariableNotExistingException;
import fr.unice.polytech.modalis.familiar.parser.FMLCommandInterpreter;
import fr.unice.polytech.modalis.familiar.parser.VariableAmbigousConflictException;
import fr.unice.polytech.modalis.familiar.variable.ConfigurationVariable;
import fr.unice.polytech.modalis.familiar.variable.FeatureModelVariable;
import fr.unice.polytech.modalis.familiar.variable.SetVariable;
import fr.unice.polytech.modalis.familiar.variable.Variable;
import fr.unice.polytech.modalis.familiar.variable.VariableImpl;

import static org.junit.Assert.*;

/**
 * Created by Simon Urli.
 * Edited by Ivan Logre
 */
public class Pilot {

    protected final FMLShell _shell;
    protected final FMLCommandInterpreter _environment;
    protected boolean hasBeenParsed;
    private static Logger log = Logger.getLogger(Pilot.class);
    private static Pilot instance;

    private Pilot() {
        //if (FMLShell.getInstance() == null)
        _shell = FMLShell.instantiateStandalone(System.in);
        //else
        //	_shell = FMLShell.getInstance();
        _environment = _shell.getCurrentEnv();
        _shell.setVerbose(true);
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
     * Eval file line by line: it won't work with a FM written on multiple lines.
     * @param filename
     * @throws FMEngineException, FileNotFoundException, IOException
     * @throws fr.unice.polytech.modalis.familiar.interpreter.FMLFatalError
     * @throws FMLAssertionError
     */
    public void evalFile(String filename) throws FMEngineException, FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String t = "";
        while (br.ready()) {
            t = t.concat(br.readLine());
            this.eval(t);
            t ="";
        }
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
        try {
            _shell.parse(instr);
        } catch (Exception e) {
            throw new FMEngineException("Unmanaged error :"+e+".\nInstruction: "+instr+"\n");
        }

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
    public Variable getVariable(String id) throws VariableNotExistingException,
            VariableAmbigousConflictException {

        //assert (hasBeenParsed);

        try {
            VariableImpl v = (VariableImpl) _environment.getVariable(id);
            return v;
        } catch (VariableNotExistingException e) {
            throw e;
        } catch (VariableAmbigousConflictException e) {
            throw e;
        }

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
     *
     * @param id identifier of a Configuration variable
     * @return
     * @throws VariableNotExistingException
     * @throws VariableAmbigousConflictException
     */
    public ConfigurationVariable getConfigurationVariable(String id)
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
    public FMLShell getShell() {
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
     * @return
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
}
