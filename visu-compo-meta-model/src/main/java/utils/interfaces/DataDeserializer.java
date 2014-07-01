package utils.interfaces;

import metaclasses.Data;
import model.exploitation.ConcreteData;

import java.util.HashMap;

/**
 * Created by Ivan Logre on 24/06/2014.
 */
public interface DataDeserializer {

    public ConcreteData AffectHashMapFromSerializedData(String serializedData);
}
