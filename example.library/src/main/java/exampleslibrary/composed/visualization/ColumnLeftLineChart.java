package exampleslibrary.composed.visualization;

import EntryPoint.Universe;
import constants.Consts;
import exception.*;
import metaclasses.*;
import metaclasses.resource.Arity;
import metaclasses.resource.AtomicResource;
import metaclasses.concern.Concern;
import metaclasses.concern.ConcernFactory;
import metaclasses.resource.DataType;
import metaclasses.resource.Element;
import utils.FileOperation;

import java.io.IOException;

import static model.exploitation.CodeGeneration.codeGeneration;

/**
 * Created by ivan on 03/07/2014.
 */
public class ColumnLeftLineChart {



    /*
     * This example means to illustrate the capability of designing single visualization dashboard with Discrete data
     * It has to :
     *  - instantiate a valid model (conforms to the meta model)
     *  - reduce a configuration of the feature model according to the "Discrete" criteria
     *  - generate the code of the resulting visualization
     */
    public static void main(String[] args) throws IOException, GetUniqueElementOnNonCompleteConfiguration, BadIDException, VisitorException, UnhandledFamiliarException, ReductionException, EmptyUniverseException {

         /////
        //1//  Design the model of the wanted dashboard
       /////
        Dashboard dashboard = new Dashboard();
        ConcernFactory factory = new ConcernFactory();

        // #1 visualization : Discrete & Extremum
        Visualization visu1 = new Visualization();
        AtomicResource resource = new AtomicResource(
                "External temperature in Oslo",
                "Temp",
                Arity.One,
                Consts.TEMP_SENML,
                Format.SenML,
                new Element("t", DataType.long_type),
                new Element("v",DataType.double_type));
        visu1.addResource(resource);
        visu1.addConcern(factory.Discrete());
        visu1.addConcern(factory.Extremum());
        dashboard.addVisualization(visu1);

        // #2 visualization : Continuous
        Visualization visu2 = new Visualization();
        AtomicResource resource2 = new AtomicResource(
                "Edited external temperature in Oslo",
                "NoTemp",
                Arity.One,
                Consts.TEMP_NEG_SENML,
                Format.SenML,
                new Element("t",DataType.long_type),
                new Element("v",DataType.double_type));
        Concern concern2 = factory.Continuous();
        visu2.addResource(resource2);
        visu2.addConcern(concern2);
        dashboard.addVisualization(visu2);

         /////
        //2//  Use feature model to find a suitable generable widget
       /////
        for(Visualization visu : dashboard.getVisualizationList()) {
            Universe univ = new Universe();
            univ.displayUniverseState();
            univ.reduceByConcerns(visu.getConcernNames());
            if (univ.isMinimal()) {
                visu.setLibraryName(univ.getLastLibraryName());
                visu.setWidgetName(univ.getLastWidgetName());
            }
        }



         /////
        //3//  Generation
       /////
        //Generation of the HTML code from the model
        codeGeneration(dashboard,Consts.RUNTIME_FOLDER+Consts.GENERATED_TARGET_FOLDER, "LeftColumnLine.html");
    }

}
