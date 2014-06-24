package utils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Ivan Logre on 24/06/2014.
 */
public class FileOperation {
/*
 * Read a local text file, extract and return the content as a String
 */
    public static String getStringFromLocalFile(String fileName) {
        StringBuffer data = new StringBuffer();
        try {
            InputStream ips = new FileInputStream(fileName);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String line = br.readLine();
            data.append(line);
            while ((line = br.readLine()) != null) {
                data.append("\n"+line);
            }
            br.close();
        } catch (Exception e) {
            System.err.println(e.toString() + e.getMessage());
        }
        return data.toString();
    }

    /*
     * Read a remote text file, extract and return the content as a String
     */
    public static String getStringFromRemoteFile(String urlString) {
        StringBuffer res = new StringBuffer();
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while ((inputLine = reader.readLine()) != null)
                res.append(inputLine);
            reader.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res.toString();
    }

    /*
    * Record an object in a file
    * H :   the given Object has a toString() method
    *       the given path is valid
    */
    //TODO test limits (existing file or not, append etc)
    public static void setDataFileFromObject(Object dataToPrint, String filePath, String encoding) {
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), encoding));
            writer.write(dataToPrint.toString());
            writer.close();
        } catch (IOException e) {
            System.err.println("setDataFileFromObject caused this. Untested yet.");
            e.printStackTrace();
        }
    }

    public static void setDataFileFromObject(Object dataToPrint, String filePath) {
         setDataFileFromObject(dataToPrint, filePath, "utf-8");
    }
}
