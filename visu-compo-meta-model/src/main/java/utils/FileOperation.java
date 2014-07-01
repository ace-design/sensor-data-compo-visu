package utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.Random;

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
            return "{}";
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

    //Creation of the folder if it doesn't exist already
    public static void setUpFolder(String folderName) {
        File f = new File(Paths.get("").toAbsolutePath().toString() + folderName);
        if ((!f.exists()) || (!f.isDirectory())) {
            if (!f.mkdirs()) {
                try {
                    throw new IOException("Can't create the /product folder !");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * This function copy a serialized JSON string to return slightly different data set
     * Post-condition :  each data value is randomize by plus or minus the given parameter
     */
    //TODO make a test validating the capability to create a new dataset from an existing one
    public static void CreateNewDataFile(String serializedData, String newDataName){
        try {
            JSONObject rootSource = new JSONObject(serializedData);

            StringWriter dataToPrint = new StringWriter();
            JSONWriter rootDest = new JSONWriter(dataToPrint);
            rootDest.object();
            rootDest.key("bn");
            rootDest.value(rootSource.get("bn")+"v2");
            rootDest.key("bt");
            rootDest.value(rootSource.get("bt"));
            rootDest.key("e");
            rootDest.array();
            JSONArray values = rootSource.getJSONArray("e");
            Random delta = new Random();
            for (int i = 0; i < values.length(); i++) {
                JSONObject v = values.getJSONObject(i);
                rootDest.object();
                //rootDest.key("u");
                //rootDest.value(v.getString("u"));
                rootDest.key("t");
                rootDest.value(v.getInt("t"));
                rootDest.key("v");
                rootDest.value(v.getInt("v")+delta.nextInt(7));
                rootDest.endObject();
            }
            rootDest.endArray();
            rootDest.endObject();
            FileOperation.fillFileFromObject(dataToPrint,"products/"+newDataName+".senml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
