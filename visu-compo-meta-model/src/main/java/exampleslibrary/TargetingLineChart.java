package exampleslibrary;

import metaclasses.Concern;
import metaclasses.Data;
import metaclasses.Visualization;

import static utils.CodeGeneration.codeGeneration;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
public class TargetingLineChart {

    /*
     * This example means to illustrate the capability of designing single visualization dashboard with continuous data
     * It has to :
     *  - instantiate a valid model (conforms to the meta model)
     *  - reduce a configuration of the feature model according to the "continuous" criteria
     *  - generate the code of the resulting visualization
     */
    public static void main(String[] args) {
        Data data = new Data("http://users.polytech.unice.fr/~logre/resources/temp2.senml");
        Concern concern = Concern.continuous;
        Visualization visu = new Visualization(data, concern);
        codeGeneration("", "");
    }

}
