package exampleslibrary;

import EntryPoint.Library;

import java.nio.file.Paths;

/**
 * Created by Ivan Logre on 02/07/2014.
 */
public class FeatureModelMerge {
    public static void main(String[] args) {
        Library lib1 = new Library(Paths.get("").toAbsolutePath().toString() + "/familiar-pilot/src/main/resources/fms_1.fml");
        Library lib2 = new Library(Paths.get("").toAbsolutePath().toString() + "/familiar-pilot/src/main/resources/fms_2.fml");

        Library newlib1 = lib1.merge(lib2);
        Library newlib2 = lib2.merge(lib1);

    }
}
