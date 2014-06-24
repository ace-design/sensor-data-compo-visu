/**
 * Created by Ivan Logre on 23/06/2014.
 */


import static org.junit.Assert.*;

import exception.FMEngineException;
import kernel.Pilot;
import org.junit.Test;

import fr.unice.polytech.modalis.familiar.interpreter.VariableNotExistingException;
import fr.unice.polytech.modalis.familiar.parser.VariableAmbigousConflictException;
import fr.unice.polytech.modalis.familiar.variable.Variable;

public class SimpleFamiliarTest {
    @Test
    public void testToStringRunCorrectlyInFamiliar() throws FMEngineException, VariableNotExistingException, VariableAmbigousConflictException {
        Pilot fam_pilot = Pilot.getInstance();
        System.out.println("Instantiation of Pilot");
        String FMA = "fma = FM(A: B C [D]; C: (X|Y); D: (Q|T|R)+;)";
        fam_pilot.eval(FMA);
        System.out.println("Eval de FMA");
        Variable fmav = fam_pilot.getVariable("fma");
        String expected = "A: [D] B C ; \n"
                +"D: (R|T|Q)+ ; \n"
                +"C: (X|Y) ;";
        String s = fmav.toString();
        System.out.println("String computed: "+s);
        fam_pilot.clearInterpreter();
        assertEquals(expected,s);
    }
}
