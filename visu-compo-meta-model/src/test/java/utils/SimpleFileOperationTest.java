package utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Ivan Logre on 24/06/2014.
 */
public class SimpleFileOperationTest {

    private File file;
    private Writer writer;
    private final String fileName = "./imatest";

    @Before
    public void setUp() throws IOException {
        file = new File(fileName);
        file.createNewFile();
        file.setWritable(true);
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),"utf-8"));
    }


    @Test
    public void testReadFileContainingStringToString() throws IOException {
        String common = "imatest";
        writer.write(common);
        writer.flush();
        assertEquals(common, FileOperation.getStringFromFile(fileName));
    }

    @Test
    public void testReadFileContainingMultiLineString() throws IOException {
        String common = "imatest \n and me too";
        writer.write(common);
        writer.flush();
        assertEquals(common, FileOperation.getStringFromFile(fileName));
    }

    @Test
    public void testReadFileContainingJSONToString() throws IOException {
        String common = "{\"bn\":\"EBike1/ctrl_tempd2\",\"bt\":1338740136,\"e\":[{\"t\":0,\"v\":19},{\"t\":1,\"v\":21}]}";
        writer.write(common);
        writer.flush();
        assertEquals(common, FileOperation.getStringFromFile(fileName));
    }

    @Test(expected = FileNotFoundException.class)
    public void testReadNonExistingRemoteFile() throws IOException {
        assertNotNull(FileOperation.getStringFromFile("http://users.polytech.unice.fr/~logre/resources/iDontExist.senml"));

    }

    @Test(expected = FileNotFoundException.class)
    public void testReadNonExistingLocalFile() throws IOException {
        assertNotNull(FileOperation.getStringFromFile("/iDontExist.senml"));
    }

    @Test(expected = NullPointerException.class)
    public void testReadNullFile() throws IOException {
        assertNotNull(FileOperation.getStringFromFile(null));
    }

    @Test
    public void testReadLocalFileContainingRemoteClue() throws IOException {
        File file = new File("./imatest_http_localhost.senml");
        file.createNewFile();
        file.setWritable(true);
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./imatest_http_localhost.senml"),"utf-8"));
        writer.write("imatest");
        writer.flush();
        assertEquals("imatest",FileOperation.getStringFromFile(file.getPath()));
        file.delete();
    }

    @After
    public void cleanUp() throws IOException {
        writer.close();
        //noinspection ResultOfMethodCallIgnored
        file.delete();
    }

}
