package utils.implementation;

import model.exploitation.ConcreteData;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.interfaces.DataDeserializer;


/**
 * Created by Ivan Logre on 01/07/2014.
 */
public class SenMLDeserializer implements DataDeserializer {

    /*
     * This function deserialize a JSON string to return an hashmap according to the senml format description
     * H :  bt = base time, e = set of value
     *      t = time of capture of this specific value relatively to the base time, v = value
     * /!\ in test mode, ignore the base time to temporally align two datasets
     */
    public ConcreteData AffectHashMapFromSerializedData(String serializedData){
        ConcreteData res = new ConcreteData();
        try {
            JSONObject rootSource = new JSONObject(serializedData);
            int baseTime = rootSource.getInt("bt");
            res.setName(rootSource.getString("bn"));
            //TODO offer to use the basetime for a absolute date or to ignore it for a relative one
            JSONArray values = rootSource.getJSONArray("e");
            for (int i = values.length() - 1; i >= 0; i--) {
                JSONObject v = values.getJSONObject(i);
                res.put(v.getDouble("t") + baseTime, v.getDouble("v"));
                //res.put(v.getInt("t")+baseTime, v.getInt("v"));  // allow to compare factorize data set with different basetime
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


}
