package exampleslibrary;

import EntryPoint.Library;
import constants.Consts;
import exception.BadIDException;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by Ivan Logre on 02/07/2014.
 */
public class FeatureModelMerge {
    public static void main(String[] args) throws IOException, BadIDException {
        Library lib1 = new Library(Consts.RUNTIME_FOLDER+ "/familiar-pilot/src/main/resources/fms_1.fml");
        Library lib2 = new Library(Consts.RUNTIME_FOLDER+"/familiar-pilot/src/main/resources/fms_2.fml");

        lib1.displayLibraryState();
        lib2.displayLibraryState();

        Library newLib1 = Library.merge(lib1,lib2);
        newLib1.displayLibraryState();
    }
}
