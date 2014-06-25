package utils;

import org.json.JSONWriter;

import java.io.StringWriter;
import java.util.HashMap;

import static utils.DataDeserializer.AffectHashMapFromSerializedSenMLData;

/**
 * Created by Ivan Logre on 25/06/2014.
 */
public class DataConverter {

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

}
