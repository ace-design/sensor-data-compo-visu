package utils;

import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

import java.nio.file.Paths;

/**
 * Created by Ivan Logre on 25/06/2014.
 */
public class CodeGeneration {

    public static String codeGeneration(String visuName, String data){
        STGroup group = new STGroupDir(Paths.get("").toAbsolutePath().toString()+"/visu-compo-meta-model/src/main/resources/stringtemplates/",'$', '$');

        return group.toString();
    }
}
