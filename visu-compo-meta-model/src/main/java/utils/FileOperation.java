package utils;

import org.codehaus.plexus.util.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

/**
 * Created by Ivan Logre on 24/06/2014.
 */
public class FileOperation {
    /*
     * Read a local text file, extract and return the content as a String
     */
    public static String getStringFromFile(String dataSource) throws IOException {
        //the extraction is not the same if it's a remote file or a local one.
        if (dataSource.contains("http://") || dataSource.contains("localhost:"))
           return FileOperation.getStringFromRemoteFile(dataSource);
        else
            return FileOperation.getStringFromLocalFile(dataSource);
    }

/*
 * Read a local text file, extract and return the content as a String
 */
    private static String getStringFromLocalFile(String fileName) throws IOException {
        StringBuilder data = new StringBuilder();
        InputStream ips = new FileInputStream(fileName);
        InputStreamReader ipsr = new InputStreamReader(ips);
        BufferedReader br = new BufferedReader(ipsr);
        String line = br.readLine();
        data.append(line);
        while (null != (line = br.readLine())) {
            data.append("\n");
            data.append(line);
        }
        br.close();
        return data.toString();
    }

    /*
     * Read a remote text file, extract and return the content as a String
     */
    private static String getStringFromRemoteFile(String urlString) throws IOException {
        StringBuilder res = new StringBuilder();

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;

        while ((inputLine = reader.readLine()) != null)
            res.append(inputLine+"\n");
        reader.close();

        return res.toString();
    }

    /*
    * Record an object in a file
    * H :   the given Object has a toString() method
    *       the given path is valid
    * If the file contain something, it is not append, the old content is forget.
    */
    private static void fillFileFromObject(Object dataToPrint, String filePath, String encoding) throws IOException {
        File f = new File(filePath);
        if (!f.getParentFile().exists())
            throw new IOException("---> Please check that your path is valid (all mentioned directories have to exist)");
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), encoding));
        writer.write(dataToPrint.toString());
        writer.flush();
        writer.close();
    }

    public static void fillFileFromObject(Object dataToPrint, String filePath) throws IOException {
         fillFileFromObject(dataToPrint, filePath, "utf-8");
    }

    //Creation of the folder if it doesn't exist already
    public static void setUpFolder(String folderName) throws IOException {
        if(folderName!=null) {
            File f = new File(Paths.get("").toAbsolutePath().toString() +"/"+ folderName);
            if ((!f.exists()) || (!f.isDirectory())) {
                if (!f.mkdirs()) {
                    throw new IOException("Can't create the /product folder !");
                }
            }
        }else
            throw new IOException("Creating a \"null\" folder, really ?");
    }

    public static void copyFile(String dataSource,String destination) throws IOException {
        //the extraction is not the same if it's a remote file or a local one.
        if (dataSource.contains("http://") || dataSource.contains("https://") || dataSource.contains("localhost:"))
            FileOperation.copyRemoteFile(dataSource, destination);
        else
            FileOperation.copyLocalFile(dataSource, destination);
    }

    private static void copyRemoteFile(String dataSource, String destination) throws IOException {
        URL in = new URL(dataSource);
        File out = new File(destination);
        FileUtils.copyURLToFile(in, out);
    }

    private static void copyLocalFile(String dataSource, String destination) throws IOException {
        Files.copy(new File(dataSource).toPath(), new File(destination).toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    //
}
