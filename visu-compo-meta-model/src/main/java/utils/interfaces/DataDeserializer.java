package utils.interfaces;

import model.exploitation.ConcreteData;

/**
 * Created by Ivan Logre on 24/06/2014.
 */
public interface DataDeserializer {

    public ConcreteData getConcreteDataFromSerializedData(String serializedData, String indexName, String columnName);
}
