import EntryPoint.Library;
import exception.BadIDException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by ivan on 07/07/2014.
 */
public class TestMerge {

    @Test
    public void testFamiliarMergeCounting() throws BadIDException, IOException {
        Library lib1 = new Library(this.getClass().getResource("fms_1.fml").getPath());
        Library lib2 = new Library(this.getClass().getResource("fms_2.fml").getPath());

        double count = lib1.getNumberValidConfiguration() + lib2.getNumberValidConfiguration();

        Library merged = Library.merge(lib1,lib2);

        System.out.println("_____________");
        System.out.println("MergeCounting");
        System.out.println("Expected : "+count);
        System.out.println("Obtained : "+merged.getNumberValidConfiguration());
        assertEquals(count, merged.getNumberValidConfiguration(),0);
    }

    @Test
    public void testFamiliarMergeCommutativity() throws BadIDException, IOException {
        Library lib1 = new Library(this.getClass().getResource("fms_1.fml").getPath());
        Library lib2 = new Library(this.getClass().getResource("fms_2.fml").getPath());

        Library merged1 = Library.merge(lib1,lib2);

        Library lib3 = new Library(this.getClass().getResource("fms_1.fml").getPath());
        Library lib4 = new Library(this.getClass().getResource("fms_2.fml").getPath());

        Library merged2 = Library.merge(lib3,lib4);

        System.out.println("__________________");
        System.out.println("MergeCommutativity");
        System.out.println("Expected : "+merged1.getNumberValidConfiguration());
        System.out.println("Obtained : "+merged2.getNumberValidConfiguration());
        assertEquals(merged2.getNumberValidConfiguration(), merged1.getNumberValidConfiguration(),0);
    }


    @Test
    public void testFamiliarMergeCommutativity2() throws BadIDException, IOException {
        Library lib1 = new Library(this.getClass().getResource("fms_1.fml").getPath());
        Library lib2 = new Library(this.getClass().getResource("fms_2.fml").getPath());

        Library merged1 = Library.merge(lib1,lib2);
        Library merged2 = Library.merge(lib2,lib1);

        System.out.println("__________________");
        System.out.println("MergeCommutativity2");
        System.out.println("Expected : "+merged1.getNumberValidConfiguration());
        System.out.println("Obtained : "+merged2.getNumberValidConfiguration());
        assertEquals(merged2.getNumberValidConfiguration(), merged1.getNumberValidConfiguration(),0);
    }


}
