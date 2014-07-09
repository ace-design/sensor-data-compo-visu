package utils.implementation;

import model.exploitation.ConcreteData;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.interfaces.DataDeserializer;

import java.util.List;

/**
 * Created by Ivan Logre on 01/07/2014.
 */
public class SmartCampusDeserializer implements DataDeserializer {

    //TODO comment
    public ConcreteData getConcreteDataFromSerializedData(String serializedData, String indexName, String columnName){
        ConcreteData res = new ConcreteData();
        try {
            JSONObject rootSource = new JSONObject(serializedData);
            res.setName(rootSource.getString("id"));
            JSONArray values = rootSource.getJSONArray("values");
            for (int i = values.length() - 1; i >= 0; i--) {
                JSONObject v = values.getJSONObject(i);
                res.put(v.getDouble(indexName), v.getDouble(columnName));  // allow to compare factorize data set with different basetime
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
