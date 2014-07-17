package exampleslibrary.composed.visualization;

import EntryPoint.Universe;
import constants.Consts;
import exception.*;
import metaclasses.*;
import utils.FileOperation;

import java.io.IOException;

import static model.exploitation.CodeGeneration.codeGeneration;

/**
 * Created by ivan on 17/07/2014.
 */
public class MultipleLineChart {

    public static void main(String[] args) throws IOException, GetUniqueElementOnNonCompleteConfiguration, BadIDException, UnhandledDataFormatException, UnhandledFamiliarException, ReductionException, EmptyUniverseException {

        /////
        //1//  Design the model of the wanted dashboard
        /////
        Dashboard dashboard = new Dashboard();

        // #1 visualization : Discrete & Extremum
        Visualization visu1 = new Visualization();
        Data data = new Data(Consts.TEMP_SENML, Format.SenML);
        visu1.addData(data);
        visu1.addConcern(Concern.Continuous);

        // #2 visualization : Continuous
        Visualization visu2 = new Visualization();
        Data data2 = new Data(Consts.TEMP_NEG_SENML, Format.SenML);
        Concern concern2 = Concern.Continuous;
        visu2.addData(data2);
        visu2.addConcern(concern2);

        Visualization visu = Visualization.Fusion(visu1, visu2);
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
        }

        /////
        //3//  Generation
        /////
        //Generation of the HTML code from the model
        String code = codeGeneration(dashboard);
        //Creation of the /product folder if it doesn't exist already
        FileOperation.setUpFolder(Consts.GENERATED_TARGET_FOLDER);
        //store the resulting visualization in a file named after the used concern
        FileOperation.fillFileFromObject(code, Consts.RUNTIME_FOLDER+Consts.GENERATED_TARGET_FOLDER + "MultipleLine.html");
    }
}
