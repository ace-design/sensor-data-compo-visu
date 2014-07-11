import EntryPoint.Universe;
import exception.BadIDException;
import exception.UnhandledFamiliarException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by ivan on 07/07/2014.
 */
public class TestMerge {

    @Test
    public void testFamiliarMergeCounting() throws BadIDException, IOException, UnhandledFamiliarException {
        Universe universe1 = new Universe(this.getClass().getResource("fms_1.fml").getPath());
        Universe universe2 = new Universe(this.getClass().getResource("fms_2.fml").getPath());
        double count = universe1.getNumberSuitableTargets() + universe2.getNumberSuitableTargets();
        Universe merged = Universe.merge(universe1,universe2);
        assertEquals(count, merged.getNumberSuitableTargets(),0);
    }

    @Test
    public void testFamiliarMergeCommutativity() throws BadIDException, IOException, UnhandledFamiliarException {
        Universe universe1 = new Universe(this.getClass().getResource("fms_1.fml").getPath());
        Universe universe2 = new Universe(this.getClass().getResource("fms_2.fml").getPath());
        Universe merged1 = Universe.merge(universe1,universe2);

        Universe universe3 = new Universe(this.getClass().getResource("fms_1.fml").getPath());
        Universe universe4 = new Universe(this.getClass().getResource("fms_2.fml").getPath());
        Universe merged2 = Universe.merge(universe4,universe3);

        assertEquals(merged2.getNumberSuitableTargets(), merged1.getNumberSuitableTargets(),0);
    }


    @Test
    public void testFamiliarMergeCommutativity2() throws BadIDException, IOException, UnhandledFamiliarException {
        Universe universe1 = new Universe(this.getClass().getResource("fms_1.fml").getPath());
        Universe universe2 = new Universe(this.getClass().getResource("fms_2.fml").getPath());
        Universe merged1 = Universe.merge(universe1,universe2);
        Universe merged2 = Universe.merge(universe2,universe1);

        assertEquals(merged2.getNumberSuitableTargets(), merged1.getNumberSuitableTargets(),0);
    }


}
