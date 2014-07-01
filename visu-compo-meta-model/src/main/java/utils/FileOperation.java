package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Ivan Logre on 24/06/2014.
 */
public class FileOperation {
    /*
     * Read a local text file, extract and return the content as a String
     */
    public static String getStringFromFile(String dataSource) {
        //the extraction is not the same if it's a remote file or a local one.
        if (dataSource.contains("http://") || dataSource.contains("localhost"))
           return FileOperation.getStringFromRemoteFile(dataSource);
        else
            return FileOperation.getStringFromLocalFile(dataSource);
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

    /*
     * Read a remote text file, extract and return the content as a String
     */
    public static String getStringFromRemoteFile(String urlString) {
        StringBuilder res = new StringBuilder();
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
    private static void fillFileFromObject(Object dataToPrint, String filePath, String encoding) {
        try {
            File f = new File(filePath);
            if (!f.getParentFile().exists()){
                throw new IOException("---> Please check that your path is valid (all mentioned directories have to exist)");
            }
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), encoding));
            writer.write(dataToPrint.toString());
            writer.close();
            } catch (IOException e) {
                System.err.println("---> Method FileOperation.fillFileFromObject() caused this. Untested yet.");
                e.printStackTrace();
            }
    }

    public static void fillFileFromObject(Object dataToPrint, String filePath) {
         fillFileFromObject(dataToPrint, filePath, "utf-8");
    }
}
