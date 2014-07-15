package utils.interfaces;

import model.exploitation.ConcreteData;

import java.util.HashMap;

/**
 * Created by Ivan Logre on 01/07/2014.
 */
public interface DataTargetFormater {

    /*
     * This method must be implemented to allow the transformation of a given ConcreteData representing data to display in the proper library format
     */
    public String convertData2LibraryFormat(ConcreteData data);
    public String convertTimedData2LibraryFormat(ConcreteData data);
}
