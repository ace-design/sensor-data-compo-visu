package utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Paths;

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
        assertEquals("imatest", FileOperation.getStringFromFile(file.getPath()));
        file.delete();
    }

    @Test
    public void testWriteInFile() throws IOException {
        StringBuilder s = new StringBuilder();
        s.append("imatest");
        FileOperation.fillFileFromObject(s,fileName);
        assertEquals("imatest", FileOperation.getStringFromFile(fileName));
    }

    @Test
    public void testWriteInNotEmptyFileDontAppend() throws IOException {
        String common = "imatest_old";
        writer.write(common);
        writer.flush();
        StringBuilder s = new StringBuilder();
        s.append("imatest_new");
        FileOperation.fillFileFromObject(s,fileName);
        assertEquals("imatest_new", FileOperation.getStringFromFile(fileName));
    }

    @Test
    public void testWriteInNonExistingFileImpliesCreateIt() throws IOException {
        File file = new File("./iDontExist.senml");
        if(file.exists())
            file.delete();
        StringBuilder s = new StringBuilder();
        s.append("imatest");
        FileOperation.fillFileFromObject(s,file.getPath());
        assertEquals("imatest", FileOperation.getStringFromFile(file.getPath()));
        file.delete();
    }

    @Test(expected = IOException.class)
    public void testWriteInNonExistingFolder() throws IOException {
        File file = new File("./iDontExist/imatest.senml");
        StringBuilder s = new StringBuilder();
        s.append("imatest");
        FileOperation.fillFileFromObject(s, file.getPath());
    }

    @Test
    public void testCreateFolder() throws IOException {
        String folderName = "iDontExist";
        FileOperation.setUpFolder(folderName);

        File f = new File(Paths.get("").toAbsolutePath().toString()+"/"+folderName);
        assertTrue(f.exists());
        assertTrue(f.isDirectory());

        f.delete();
    }

    @Test(expected = IOException.class)
    public void testCreateNullFolder() throws IOException {
        String folderName = null;
        FileOperation.setUpFolder(folderName);
    }

    @Test(expected = IOException.class)
    public void testBadlyNamedFolder() throws IOException {
        String folderName = "\u0000";
        FileOperation.setUpFolder(folderName);
    }

    @After
    public void cleanUp() throws IOException {
        writer.close();
        //noinspection ResultOfMethodCallIgnored
        file.delete();
    }

}
