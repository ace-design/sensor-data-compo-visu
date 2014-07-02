package exampleslibrary;

import EntryPoint.FMExposer;

import java.nio.file.Paths;

/**
 * Created by Ivan Logre on 02/07/2014.
 */
public class FeatureModelMerge {
    public static void main(String[] args) {
        FMExposer fme = new FMExposer(Paths.get("").toAbsolutePath().toString() + "/familiar-pilot/src/main/resources/fms_1.fml");
        FMExposer fme2 = new FMExposer(Paths.get("").toAbsolutePath().toString() + "/familiar-pilot/src/main/resources/fms_2.fml");

        FMExposer fme_a = fme.merge(fme2);
        FMExposer fme_b = fme2.merge(fme);

        System.out.println(fme_a);
        System.out.println(fme_b);
    }
}
