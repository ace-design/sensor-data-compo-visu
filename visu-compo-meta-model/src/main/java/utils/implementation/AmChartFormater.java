package utils.implementation;

import model.exploitation.ConcreteData;
import org.json.JSONWriter;
import utils.interfaces.DataTargetFormater;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Created by Ivan Logre on 01/07/2014.
 */
public class AmChartFormater implements DataTargetFormater {


    @Override
    public String convertData2LibraryFormat(ConcreteData data) {
        HashMap<Object,Double> HM_index_value = data.getHM_dataset();

        //transform the hashmap(time,value) in a format understandable by AmChart
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
    public String convertData2LibraryFormatSorted(ConcreteData data) {
        HashMap<Double,Double> HM_time_value = data.getHM_dataset();

        //transform the hashmap(time,value) in a format understandable by AmChart
        StringWriter dataToPrint = new StringWriter();
        JSONWriter rootDest = new JSONWriter(dataToPrint);
        rootDest.array();

        TreeSet<Double> set = new TreeSet<>(HM_time_value.keySet());
        for (Double i : set) {
            rootDest.object();
            rootDest.key(data.getCategoryFieldName());
            rootDest.value(i);
            rootDest.key(data.getValueFieldName());
            rootDest.value(HM_time_value.get(i));
            rootDest.endObject();
        }
        rootDest.endArray();

        return dataToPrint.toString();
    }
}
