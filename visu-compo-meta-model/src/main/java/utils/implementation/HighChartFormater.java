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
public class HighChartFormater implements DataTargetFormater {

//TODO
    @Override
    public String convertData2LibraryFormat(ConcreteData data) {

        HashMap<Double,Double> HM_time_value = data.getHM_dataset();

        //transform the hashmap(time,value) in a format understandable by AmChart
        StringBuilder dataToPrint = new StringBuilder();
        TreeSet<Double> set = new TreeSet<>(HM_time_value.keySet());
        dataToPrint.append("[");
        dataToPrint.append("\n");
        for (Double i : set) {
            dataToPrint.append("[");
            dataToPrint.append(i);
            dataToPrint.append(",");
            dataToPrint.append(HM_time_value.get(i));
            dataToPrint.append("],");
            dataToPrint.append("\n");
        }
        dataToPrint.deleteCharAt(dataToPrint.lastIndexOf(","));
        dataToPrint.append("\n");
        dataToPrint.append("]");


        return dataToPrint.toString();
    }
}