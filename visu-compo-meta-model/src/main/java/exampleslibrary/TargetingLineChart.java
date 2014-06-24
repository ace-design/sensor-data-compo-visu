package exampleslibrary;

import metaclasses.Concern;
import metaclasses.Data;
import metaclasses.Visualization;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
public class TargetingLineChart {

    public static void main(String[] args) {
        Data data = new Data("http://users.polytech.unice.fr/~logre/resources/temp2.senml");
        Concern concern = Concern.continuous;
        Visualization visu = new Visualization(data, concern);
    }

}
