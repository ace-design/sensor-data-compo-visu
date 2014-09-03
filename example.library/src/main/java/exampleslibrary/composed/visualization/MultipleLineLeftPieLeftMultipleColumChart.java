package exampleslibrary.composed.visualization;

import EntryPoint.Universe;
import constants.Consts;
import exception.*;
import metaclasses.*;
import metaclasses.resource.Arity;
import metaclasses.resource.AtomicResource;
import metaclasses.concern.ConcernFactory;
import metaclasses.resource.DataType;
import metaclasses.resource.Element;
import utils.FileOperation;

import java.io.IOException;

import static model.exploitation.CodeGeneration.codeGeneration;

/**
 * Created by ivan on 17/07/2014.
 */
public class MultipleLineLeftPieLeftMultipleColumChart {

    public static void main(String[] args) throws IOException, GetUniqueElementOnNonCompleteConfiguration, BadIDException, VisitorException, UnhandledFamiliarException, ReductionException, EmptyUniverseException {

        /////
        //1//  Design the model of the wanted dashboard
        /////
        Dashboard dashboard = new Dashboard();
        ConcernFactory factory = new ConcernFactory();

        // First composite visu //

            // #1 visualization : Continuous
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
            visu1.addConcern(factory.Extremum());
            visu1.addConcern(factory.Discrete());

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
            visu2.addConcern(factory.Discrete());
            visu2.addConcern(factory.Extremum());
            visu2.addResource(resource2);

            // #3 visualization : Continuous
            Visualization visu3 = new Visualization();
            AtomicResource resource3 = new AtomicResource(
                "Ground speed of a bike in Oslo",
                "Speed",
                Arity.One,
                Consts.SPEED_SENML,
                Format.SenML,
                new Element("t",DataType.long_type),
                new Element("v",DataType.double_type));
            visu3.addConcern(factory.Extremum());
            visu3.addConcern(factory.Discrete());
            visu3.addResource(resource3);

            Visualization visuLine = Visualization.Fusion(Visualization.Fusion(visu1, visu2), visu3);
            dashboard.addVisualization(visuLine);



        // Second simple visu //

            // #4 visualization : Proportion
            Visualization visu4 = new Visualization();
            AtomicResource resource4 = new AtomicResource(
                "Proportion of the temperature in the office under 27°, above 30° and between",
                "Proportion",
                Arity.One,
                Consts.CATEGORIZED_STACKED,
                Format.Stacked,
                new Element("range",DataType.textual_type),
                new Element("volume",DataType.double_type));
            visu4.addResource(resource4);
            visu4.addConcern(factory.Proportion());

            dashboard.addVisualization(visu4);



        // First composite visu //

            // #5 visualization : Continuous
            Visualization visu5 = new Visualization();
            AtomicResource resource5 = new AtomicResource(
                "Luminosity of the 442 office",
                "Light442",
                Arity.Many,
                Consts.Raw_LIGHT_SMARTCAMPUS,
                Format.SmartCampus,
                new Element("date",DataType.long_type),
                new Element("value",DataType.double_type));
            visu5.addResource(resource5);
            visu5.addConcern(factory.Continuous());

            // #6 visualization : Continuous
            Visualization visu6 = new Visualization();
            AtomicResource resource6 = new AtomicResource(
                "Temperature of the 442 office",
                "Temp442",
                Arity.Many,
                Consts.Raw_TEMP_SMARTCAMPUS,
                Format.SmartCampus,
                new Element("date",DataType.long_type),
                new Element("value",DataType.double_type));
            visu6.addResource(resource6);
            visu6.addConcern(factory.Continuous());

            Visualization visuCol = Visualization.Fusion(visu5, visu6);
            dashboard.addVisualization(visuCol);

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
       codeGeneration(dashboard,Consts.RUNTIME_FOLDER+Consts.GENERATED_TARGET_FOLDER,"MultipleLeft.html");
    }
}
