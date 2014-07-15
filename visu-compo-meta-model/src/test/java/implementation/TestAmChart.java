package implementation;

import model.exploitation.ConcreteData;
import org.junit.Test;
import utils.implementation.AmChartFormater;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ivan on 03/07/2014.
 */
public class TestAmChart {

    @Test
    public void testConvertIntPairToAmChartDataFormat()  {
        ConcreteData data = new ConcreteData();
        data.put(1.0,99.0);
        data.put(2.0,98.0);
        assertEquals("[{\"t\":\"70-01-01 01:00:01\",\"v\":99},{\"t\":\"70-01-01 01:00:02\",\"v\":98}]",new AmChartFormater().convertTimedData2LibraryFormat(data));
    }

    @Test
    public void testConvertDoublePairToAmChartDataFormat()  {
        ConcreteData data = new ConcreteData();
        data.put(1.1,99.1);
        data.put(2.1,98.1);
        assertEquals("[{\"t\":\"70-01-01 01:00:01\",\"v\":99.1},{\"t\":\"70-01-01 01:00:02\",\"v\":98.1}]",new AmChartFormater().convertTimedData2LibraryFormat(data));
    }

    @Test(expected = NullPointerException.class)
    public void testCantConvertNullDataToAmChartDataFormat()  {
        ConcreteData data = null;
        new AmChartFormater().convertData2LibraryFormat(data);
    }

    @Test
    public void testConvertNullPairImpliesEmptyAmChartData()  {
        ConcreteData data = new ConcreteData();
        assertEquals("[]",new AmChartFormater().convertData2LibraryFormat(data));
    }


}
