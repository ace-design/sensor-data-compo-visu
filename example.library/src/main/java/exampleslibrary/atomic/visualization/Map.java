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
        //TODO : declare a proper resource (composed one)
        AtomicResource resource = new AtomicResource(
                "Last sensor stats on the 4th floor map",
                "LastState",
                Arity.Many,
                Consts.LAST_STATE,
                Format.Custom,
                new Element("range", DataType.textual),
                new Element("volume",DataType.numerical)
                );
        visu.addResource(resource);
        ConcernFactory factory = new ConcernFactory();
        visu.addConcern(factory.Location(Consts.SVG_DESCRIPTION));
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
            String code = codeGeneration(dashboard);
            //Creation of the /product folder if it doesn't exist already
            FileOperation.setUpFolder(Consts.GENERATED_TARGET_FOLDER);
            //store the resulting visualization in a file named after the used concern
            FileOperation.fillFileFromObject(code, Paths.get("").toAbsolutePath().toString() + Consts.GENERATED_TARGET_FOLDER + "Map.html");
        }
    }
}
