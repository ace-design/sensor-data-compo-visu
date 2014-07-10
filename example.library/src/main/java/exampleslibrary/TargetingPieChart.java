package exampleslibrary;

import EntryPoint.Library;
import EntryPoint.Reduction;
import EntryPoint.Universe;
import constants.Consts;
import exception.*;
import metaclasses.*;
import utils.FileOperation;

import java.io.IOException;
import java.nio.file.Paths;

import static model.exploitation.CodeGeneration.codeGeneration;

/**
 * Created by ivan on 08/07/2014.
 */
public class TargetingPieChart {

    public static void main(String[] args) throws IOException, BadIDException, GetUniqueElementOnNonCompleteConfiguration, UnhandledDataFormatException, UnhandledFamiliarException, ReductionException {

         /////
        //1//  Design the model of the wanted dashboard
       /////
        Dashboard dashboard = new Dashboard();
        Visualization visu = new Visualization(new Data(Consts.CATEGORIZED_STACKED, new Column("range","range"), new Column("volume","scalar"), Format.Stacked),Concern.Proportion);
        dashboard.addVisualization(visu);


         /////
        //2//  Use feature model to find a suitable generable widget
       /////
        Universe univ = new Universe();
        univ.displayUniverseState();
        univ.reduceByConcerns(visu.getConcernNames());
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
            FileOperation.fillFileFromObject(code, Paths.get("").toAbsolutePath().toString() + Consts.GENERATED_TARGET_FOLDER + "PieChart.html");
        }
    }
}
