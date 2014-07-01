package utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.io.*;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Ivan Logre on 24/06/2014.
 */
class DataDeserializer {

    /*
     * This function deserialize a JSON string to return an hashmap according to the senml format description
     * H :  bt = base time, e = set of value
     *      t = time of capture of this specific value relatively to the base time, v = value
     * /!\ in test mode, ignore the base time to temporally align two datasets
     * result : an hash
     */
    public static HashMap<Integer,Integer> AffectHashMapFromSerializedSenMLData(String serializedData){
        HashMap<Integer,Integer> res = new HashMap<>();
        try {
            JSONObject rootSource = new JSONObject(serializedData);
            int baseTime = rootSource.getInt("bt");
            //TODO offer to use the basetime for a absolute date or to ignore it for a relative one
            JSONArray values = rootSource.getJSONArray("e");
            for (int i = values.length() - 1; i >= 0; i--) {
                JSONObject v = values.getJSONObject(i);
                //res.put(v.getInt("t") + baseTime, v.getInt("v"));
                res.put(v.getInt("t"), v.getInt("v"));  // allow to compare factorize data set with different basetime
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    //TODO comment
    public static HashMap<Integer,Integer> AffectHashMapFromSerializedSmartCampusData(String serializedData){
        HashMap<Integer,Integer> res = new HashMap<>();
        try {
            JSONObject rootSource = new JSONObject(serializedData);
            JSONArray values = rootSource.getJSONArray("values");
            for (int i = values.length() - 1; i >= 0; i--) {
                JSONObject v = values.getJSONObject(i);
                res.put(v.getInt("date"), v.getInt("value"));  // allow to compare factorize data set with different basetime
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /*
     * This function copy a serialized JSON string to return slightly different data set
     * Post-condition :  each data value is randomize by plus or minus the given parameter
     */
    //TODO make a test validating the capability to create a new dataset from an existing one
    public static void CreateNewDataFile(String serializedData, String newDataName){
        try {
            JSONObject rootSource = new JSONObject(serializedData);

            StringWriter dataToPrint = new StringWriter();
            JSONWriter rootDest = new JSONWriter(dataToPrint);
            rootDest.object();
            rootDest.key("bn");
            rootDest.value(rootSource.get("bn")+"v2");
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
                rootDest.value(v.getInt("v")+delta.nextInt(7));
                rootDest.endObject();
            }
            rootDest.endArray();
            rootDest.endObject();
            FileOperation.fillFileFromObject(dataToPrint,"products/"+newDataName+".senml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
