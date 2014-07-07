/**
 * Created by Ivan Logre on 23/06/2014.
 */


import static org.junit.Assert.*;

import exception.FMEngineException;
import fr.familiar.variable.FeatureModelVariable;
import kernel.Pilot;
import org.junit.Test;

import fr.familiar.interpreter.VariableNotExistingException;
import fr.familiar.parser.VariableAmbigousConflictException;
import fr.familiar.variable.Variable;

public class SimpleFamiliarTest {
    @Test
    public void testToStringRunCorrectlyInFamiliar() throws FMEngineException, VariableNotExistingException, VariableAmbigousConflictException {
        Pilot fam_pilot = Pilot.getInstance();
        System.out.println("Instantiation of Pilot");
        String FMA = "fma = FM(A: B C [D]; C: (X|Y); D: (Q|T|R)+;)";
        fam_pilot.eval(FMA);
        System.out.println("Eval de FMA");
        FeatureModelVariable fmav = fam_pilot.getFMVariable("fma");
        /*String expected = "A: [D] B C ; \n"
                +"D: (R|T|Q)+ ; \n"
                +"C: (X|Y) ;";
        String s = fmav.toString();
        */
        int counting_expected = 16;
        int counting_fmav = (int)fmav.counting();
        fam_pilot.clearInterpreter();
        assertEquals(counting_expected,counting_fmav);
    }
}
