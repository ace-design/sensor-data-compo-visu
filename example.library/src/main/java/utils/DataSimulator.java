package utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.io.*;
import java.util.*;

/**
 * Created by ivan on 09/07/2014.
 */
public class DataSimulator {



    public static void main(String[] args) throws IOException {
        /*Resource resource = new Resource("ToPorportion",Consts.TEMP_SENML, Format.SenML);
        HashMap<Double, Double> dataSource = new SenMLDeserializer().extractInstancesFromResource(resource).getTuples();
        List<Double> remarkValues = new ArrayList<>();
        remarkValues.add((double) 27);
        remarkValues.add((double) 30);
        HashMap<String,Double> categorizedValues = SenML2CategorizedByRemarkableValues(remarkValues,dataSource);
        FileOperation.fillFileFromObject(StackedSerializer(categorizedValues),Consts.RUNTIME_FOLDER+Consts.GENERATED_TARGET_FOLDER+"categorizedData.senml");*/
    }

    /*
     * Categorize the value of an input data set (key value) in pairs of (range,volume) according to a set of remarkable values
     * The ranges will be strings as following :
     *      "-INF_min(remarkableValues)"
     *      "max(remarkableValues)_+INF)"
     *      and between to consecutive remarkable values i & j, i<j :
     *          "i-j"
     * H : there is no doubloons in the remarkable values
     */
    public static HashMap<String,Double> SenML2CategorizedByRemarkableValues(List<Double> remarkValues, HashMap<Double, Double> dataSource){

        HashMap<String,Double> categorization = new HashMap<>();
        remarkValues.sort(Comparator.<Double>reverseOrder());
        for(Double rv : remarkValues)
            if(remarkValues.indexOf(rv)==0)
                categorization.put(("> "+rv),0.0);
            else
                categorization.put(rv+" - "+remarkValues.get(remarkValues.indexOf(rv) - 1),0.0);
        categorization.put(("under "+remarkValues.get(remarkValues.size() - 1)),0.0);

        for(Double key : dataSource.keySet()){
            Double value = dataSource.get(key);
            String range = getRangeFromValue(remarkValues, value);
            categorization.replace(range,categorization.get(range),categorization.get(range)+1);
        }

        return categorization;
    }

    private static String getRangeFromValue(List<Double> remarkValues, Double value) {
        remarkValues.sort(Comparator.<Double>reverseOrder());
        for(Double rv : remarkValues)
            if(value>rv && remarkValues.indexOf(rv)==0)
               return "> "+rv;
            else if (value>rv)
                return rv+" - "+remarkValues.get(remarkValues.indexOf(rv)-1);
        return "under "+remarkValues.get(remarkValues.size()-1);
    }

    public static String StackedSerializer(HashMap<String,Double> dataset){

        StringWriter dataToPrint = new StringWriter();
        JSONWriter rootDest = new JSONWriter(dataToPrint);
        rootDest.object();
        rootDest.key("bn");
        rootDest.value("categorizedData");
        rootDest.key("e");
        rootDest.array();

        for (String s : dataset.keySet()) {
            rootDest.object();
            rootDest.key("range");
            rootDest.value(s);
            rootDest.key("volume");
            rootDest.value(dataset.get(s));
            rootDest.endObject();
        }
        rootDest.endArray();
        rootDest.endObject();

        return dataToPrint.toString();
    }

    public static String CreateRandomizedSenMLDataFile(String dataSource){
        JSONObject rootSource = new JSONObject(dataSource);

        StringWriter dataToPrint = new StringWriter();
        JSONWriter rootDest = new JSONWriter(dataToPrint);
        rootDest.object();
        rootDest.key("bn");
        rootDest.value(rootSource.get("bn")+"d2");
        rootDest.key("bt");
        rootDest.value(rootSource.get("bt"));
        rootDest.key("e");
        rootDest.array();
        JSONArray values = rootSource.getJSONArray("e");
        Random delta = new Random();
        for (int i = 0; i < values.length(); i++) {
            JSONObject v = values.getJSONObject(i);
            rootDest.object();
            //rootDest.key("u");
            //rootDest.value(v.getString("u"));
            rootDest.key("t");
            rootDest.value(v.getInt("t"));
            rootDest.key("v");
            rootDest.value(v.getInt("v")+(delta.nextInt(Integer.MAX_VALUE)%30));
            rootDest.endObject();
        }
        rootDest.endArray();
        rootDest.endObject();

        return dataToPrint.toString();
    }


}
