package utils.implementation;

import model.exploitation.ConcreteData;
import org.json.JSONWriter;
import utils.interfaces.DataTargetFormater;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TreeSet;

/**
 * Created by Ivan Logre on 01/07/2014.
 */
public class AmChartFormater implements DataTargetFormater {


    @Override
    public String convertData2LibraryFormat(ConcreteData data) {
        HashMap<Object,Double> HM_index_value = data.getHM_dataset();

        //transform the hashmap(index,value) in a format understandable by AmChart
        StringWriter dataToPrint = new StringWriter();
        JSONWriter rootDest = new JSONWriter(dataToPrint);
        rootDest.array();

        for (Object i : HM_index_value.keySet()) {
            System.out.println(i.getClass());
            rootDest.object();
            rootDest.key(data.getCategoryFieldName());
            rootDest.value(i.toString());
            rootDest.key(data.getValueFieldName());
            rootDest.value(HM_index_value.get(i));
            rootDest.endObject();
        }
        rootDest.endArray();

        return dataToPrint.toString();
    }

    @Override
    public String convertTimedData2LibraryFormat(ConcreteData data) {
        HashMap<Double,Double> HM_time_value = data.getHM_dataset();

        //transform the hashmap(time,value) in a format understandable by AmChart
        StringWriter dataToPrint = new StringWriter();
        JSONWriter rootDest = new JSONWriter(dataToPrint);
        rootDest.array();

        DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.ENGLISH);

        TreeSet<Double> set = new TreeSet<>(HM_time_value.keySet());
        for (Double i : set) {
            rootDest.object();
            rootDest.key(data.getCategoryFieldName());

            Date date = new Date(i.longValue()*1000);
            rootDest.value(df.format(date));

            rootDest.key(data.getValueFieldName());
            rootDest.value(HM_time_value.get(i));
            rootDest.endObject();
        }
        rootDest.endArray();

        return dataToPrint.toString();
    }
}
