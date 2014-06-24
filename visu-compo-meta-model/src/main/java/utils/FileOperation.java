package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

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
}
