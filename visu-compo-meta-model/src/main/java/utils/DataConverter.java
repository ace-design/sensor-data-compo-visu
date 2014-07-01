package utils;

import org.json.JSONWriter;

import java.io.StringWriter;
import java.util.*;

import static utils.DataDeserializer.AffectHashMapFromSerializedSenMLData;
import static utils.DataDeserializer.AffectHashMapFromSerializedSmartCampusData;

/**
 * Created by Ivan Logre on 25/06/2014.
 */
class DataConverter {

    public static String convertSenML2AmChartFormat(String dataset){

        //deserialized the senML data in a set of pair (key,value)
        HashMap<Integer,Integer> HM_time_value = AffectHashMapFromSerializedSenMLData(dataset);

        //transform the hashmap(time,value) in a format understandable by AmChart
        StringWriter dataToPrint = new StringWriter();
        JSONWriter rootDest = new JSONWriter(dataToPrint);
        rootDest.array();

        for (Integer i : HM_time_value.keySet()) {
            rootDest.object();
            rootDest.key("t");
            rootDest.value(i);
            rootDest.key("v");
            rootDest.value(HM_time_value.get(i));
            rootDest.endObject();
        }
        rootDest.endArray();

        return dataToPrint.toString();
    }

    public static String convertSmartCampusFormat2AmChartFormat(String dataset){

        //deserialized the senML data in a set of pair (key,value)
        HashMap<Integer,Integer> HM_time_value = AffectHashMapFromSerializedSmartCampusData(dataset);

        //transform the hashmap(time,value) in a format understandable by AmChart
        StringWriter dataToPrint = new StringWriter();
        JSONWriter rootDest = new JSONWriter(dataToPrint);
        rootDest.array();

        TreeSet<Integer> set = new TreeSet<>(HM_time_value.keySet());
        for (Integer i : set) {
            rootDest.object();
            rootDest.key("t");
            rootDest.value(i);
            rootDest.key("v");
            rootDest.value(HM_time_value.get(i));
            rootDest.endObject();
        }
        rootDest.endArray();

        return dataToPrint.toString();
    }
}
