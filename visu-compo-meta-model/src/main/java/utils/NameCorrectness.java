package utils;

/**
 * Created by Ivan Logre on 01/07/2014.
 */
public class NameCorrectness {
    public static String format(String s){
        return s.replace("/","_").replace("-","_").replace("\\","-");
    }
}
