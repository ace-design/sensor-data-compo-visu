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
public class LeftPieLineMapChart {



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

        // #1 visualization : Proportion
        Visualization visu1 = new Visualization();

        AtomicResource resource = new AtomicResource(
                "Proportion of the temperature in the office under 27°, above 30° and between",
                "Proportion",
                Arity.One,
                Consts.CATEGORIZED_STACKED,
                Format.Stacked,
                new Element("range", DataType.textual_type),
                new Element("volume",DataType.double_type));
        visu1.addResource(resource);
        visu1.addConcern(factory.Proportion());
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


        // #3 visualization : Location
        Visualization visumap = new Visualization();
        visumap.addConcern(factory.Location(Consts.SVG_DESCRIPTION));

        AtomicResource resourceDoors = new AtomicResource(
                "Last door sensor stats on the 4th floor map",
                "door",
                Arity.Many,
                Consts.LAST_STATE_DOORS,
                Format.Custom,
                new Element("id", DataType.textual_type),
                new Element("kind",DataType.textual_type),
                new Element("bat",DataType.textual_type),
                new Element("value",DataType.boolean_type),
                new Element("floor",DataType.long_type),
                new Element("salle",DataType.textual_type),
                new Element("location",DataType.textual_type));
        resourceDoors.addConcern(factory.Icon(Consts.ICON_DOOR));
        visumap.addResource(resourceDoors);

        AtomicResource resourceLights = new AtomicResource(
                "Last light sensor stats on the 4th floor map",
                "light",
                Arity.Many,
                Consts.LAST_STATE_LIGHTS,
                Format.Custom,
                new Element("id", DataType.textual_type),
                new Element("kind",DataType.textual_type),
                new Element("bat",DataType.textual_type),
                new Element("value",DataType.boolean_type),
                new Element("floor",DataType.long_type),
                new Element("salle",DataType.textual_type),
                new Element("location",DataType.textual_type));
        resourceLights.addConcern(factory.Icon(Consts.ICON_LIGHT));
        visumap.addResource(resourceLights);

        AtomicResource resourceMotions = new AtomicResource(
                "Last motion sensor stats on the 4th floor map",
                "motion",
                Arity.Many,
                Consts.LAST_STATE_MOTIONS,
                Format.Custom,
                new Element("id", DataType.textual_type),
                new Element("kind",DataType.textual_type),
                new Element("bat",DataType.textual_type),
                new Element("value",DataType.boolean_type),
                new Element("floor",DataType.long_type),
                new Element("salle",DataType.textual_type),
                new Element("location",DataType.textual_type));
        resourceMotions.addConcern(factory.Icon(Consts.ICON_MOTION));
        visumap.addResource(resourceMotions);

        AtomicResource resourceTemps = new AtomicResource(
                "Last temperature sensor stats on the 4th floor map",
                "temperature",
                Arity.Many,
                Consts.LAST_STATE_TEMPS,
                Format.Custom,
                new Element("id", DataType.textual_type),
                new Element("kind",DataType.textual_type),
                new Element("bat",DataType.textual_type),
                new Element("value",DataType.double_type),
                new Element("floor",DataType.long_type),
                new Element("salle",DataType.textual_type),
                new Element("location",DataType.textual_type));
        resourceTemps.addConcern(factory.Icon(Consts.ICON_TEMP));
        visumap.addResource(resourceTemps);

        AtomicResource resourceWindows = new AtomicResource(
                "Last window sensor stats on the 4th floor map",
                "window",
                Arity.Many,
                Consts.LAST_STATE_WINDOWS,
                Format.Custom,
                new Element("id", DataType.textual_type),
                new Element("kind",DataType.textual_type),
                new Element("bat",DataType.textual_type),
                new Element("value",DataType.boolean_type),
                new Element("floor",DataType.long_type),
                new Element("salle",DataType.textual_type),
                new Element("location",DataType.textual_type));
        resourceWindows.addConcern(factory.Icon(Consts.ICON_WINDOW));
        visumap.addResource(resourceWindows);

        dashboard.addVisualization(visumap);


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
        codeGeneration(dashboard, Consts.SERVER_FOLDER, "LeftPieLineMap.html");
    }

}
