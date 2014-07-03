package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.util.UUID.randomUUID;

/**
 * Created by Ivan Logre on 02/07/2014.
 */
public class ToolBox {

    public static String newID(String prefix){
        return prefix+randomUUID().toString().replace("-","");
    }

    /*
     * Read a local text file, extract and return the content as a String
     */
    public static String getStringFromLocalFile(String fileName) {
        StringBuilder data = new StringBuilder();
        try {
            InputStream ips = new FileInputStream(fileName);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line = br.readLine();
            data.append(line);
            while ((line = br.readLine()) != null) {
                data.append("\n");
                data.append(line);
            }
            br.close();
        } catch (Exception e) {
            System.err.println(e.toString() + e.getMessage());
        }
        return data.toString();
    }
}
