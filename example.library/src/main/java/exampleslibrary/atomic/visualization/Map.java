package exampleslibrary.atomic.visualization;

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
import java.nio.file.Paths;

import static model.exploitation.CodeGeneration.codeGeneration;

/**
 * Created by ivan on 01/08/2014.
 */
public class Map {
    public static void main(String[] args) throws IOException, BadIDException, GetUniqueElementOnNonCompleteConfiguration, VisitorException, UnhandledFamiliarException, ReductionException, EmptyUniverseException {

         /////
        //1//  Design the model of the wanted dashboard
       /////
        Dashboard dashboard = new Dashboard();
        Visualization visu = new Visualization();
        ConcernFactory factory = new ConcernFactory();

        visu.addConcern(factory.Location(Consts.SVG_DESCRIPTION));

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
        visu.addResource(resourceDoors);

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
        visu.addResource(resourceLights);

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
        visu.addResource(resourceMotions);

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
        visu.addResource(resourceTemps);

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
        visu.addResource(resourceWindows);

        dashboard.addVisualization(visu);

         /////
        //2//  Use feature model to find a suitable generable widget
       /////
        Universe univ = new Universe();
        univ.displayUniverseState();
        univ.reduceByConcerns(visu.getConcernNames());
        univ.displayUniverseState();
        if (univ.isMinimal()) {
            visu.setLibraryName(univ.getLastLibraryName());
            visu.setWidgetName(univ.getLastWidgetName());

             /////
            //3//  Generation
           /////
            //Generation of the HTML code from the model
            codeGeneration(dashboard,Consts.SERVER_FOLDER,"Map.html");
        }
    }
}
