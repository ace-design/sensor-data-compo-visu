package implementation;

import model.exploitation.ConcreteData;
import org.junit.Test;
import utils.implementation.AmChartFormater;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ivan on 03/07/2014.
 */
public class TestAmChart {

    @Test
    public void testConvertIntPairToAmChartDataFormat()  {
        ConcreteData data = new ConcreteData();
        Integer i =1;
        Integer j =2;

        DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss",Locale.ENGLISH);

        data.put(i.doubleValue(),99.0);
        data.put(j.doubleValue(),98.0);

        Date dateI = new Date((long)i*1000);
        Date dateJ = new Date((long)j*1000);

        assertEquals("[{\"t\":\"" +
                        df.format(dateI)+
                "\",\"v\":99},{\"t\":\"" +
                        df.format(dateJ)+
                "\",\"v\":98}]",new AmChartFormater().convertTimedData2LibraryFormat(data));
    }

    @Test
    public void testConvertDoublePairToAmChartDataFormat()  {
        ConcreteData data = new ConcreteData();
        Double i =1.1;
        Double j =2.1;
        DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss",Locale.ENGLISH);
        data.put(i,99.1);
        data.put(j,98.1);
        Date dateI = new Date(i.longValue()*1000);
        Date dateJ = new Date(j.longValue()*1000);
        assertEquals("[{\"t\":\"" +
                        df.format(dateI)+
                "\",\"v\":99.1},{\"t\":\"" +
                        df.format(dateJ)+
                "\",\"v\":98.1}]",new AmChartFormater().convertTimedData2LibraryFormat(data));
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
