package exampleslibrary.variability;

import EntryPoint.Universe;
import constants.Consts;
import exception.BadIDException;
import exception.UnhandledFamiliarException;

import java.io.IOException;

/**
 * Created by Ivan Logre on 02/07/2014.
 */
public class UniverseDeclarationAndMerge {
    public static void main(String[] args) throws IOException, BadIDException, UnhandledFamiliarException {
        Universe univ1 = new Universe(Consts.RUNTIME_FOLDER+ "/familiar-pilot/src/main/resources/fms_1.fml");
        Universe univ2 = new Universe(Consts.RUNTIME_FOLDER+"/familiar-pilot/src/main/resources/fms_2.fml");

        univ1.displayUniverseState();
        univ2.displayUniverseState();

        Universe newLib1 = Universe.merge(univ1,univ2);
        newLib1.displayUniverseState();
    }
}
