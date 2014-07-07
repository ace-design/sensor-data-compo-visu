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
        System.out.println(Paths.get("").toAbsolutePath().toString()+"/familiar-pilot/src/main/resources/fms_1.fml");
        Library lib1 = new Library(Paths.get("").toAbsolutePath().toString()+"/familiar-pilot/src/main/resources/fms_1.fml");
        System.out.println(Paths.get("").toAbsolutePath().toString()+"/familiar-pilot/src/main/resources/fms_2.fml");
        Library lib2 = new Library(Paths.get("").toAbsolutePath().toString()+"/familiar-pilot/src/main/resources/fms_2.fml");

        Library merged1 = lib1.merge(lib2);

        Library lib3 = new Library(Paths.get("").toAbsolutePath().toString()+"/familiar-pilot/src/main/resources/fms_1.fml");
        Library lib4 = new Library(Paths.get("").toAbsolutePath().toString()+"/familiar-pilot/src/main/resources/fms_2.fml");

        Library merged2 = lib4.merge(lib3);

        System.out.println("merged1 nbConfig "+merged1.getNumberValidConfiguration());
        System.out.println("merged2 nbConfig "+merged2.getNumberValidConfiguration());

        for(String s : merged2.getWidgetsNames()){
            System.out.println(s);
        }
        /*
        Library lib1 = new Library(Consts.RUNTIME_FOLDER+ "/familiar-pilot/src/main/resources/fms_1.fml");
        Library lib2 = new Library(Consts.RUNTIME_FOLDER+"/familiar-pilot/src/main/resources/fms_2.fml");

        lib1.displayLibraryState();
        lib2.displayLibraryState();

        Library newLib1 = lib1.merge(lib2);
        newLib1.displayLibraryState();
        //TODO get the names of the 13 available widgets

        Library lib3 = new Library(Consts.RUNTIME_FOLDER+ "/familiar-pilot/src/main/resources/fms_1.fml");
        Library newLib2 = lib3.mergeWithLibraryFileFromPath(Consts.RUNTIME_FOLDER+"/familiar-pilot/src/main/resources/fms_2.fml");
        newLib2.displayLibraryState();*/

    }
}
