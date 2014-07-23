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
public class MultipleLineLeftPieLeftMultipleColumChart {

    public static void main(String[] args) throws IOException, GetUniqueElementOnNonCompleteConfiguration, BadIDException, VisitorException, UnhandledFamiliarException, ReductionException, EmptyUniverseException {

        /////
        //1//  Design the model of the wanted dashboard
        /////
        Dashboard dashboard = new Dashboard();

        // First composite visu //

            // #1 visualization : Continuous
            Visualization visu1 = new Visualization();
            Resource resource = new Resource("Temp",Consts.TEMP_SENML, Format.SenML);
            visu1.addResource(resource);
            visu1.addConcern(Concern.Extremum);
            visu1.addConcern(Concern.Discrete);

            // #2 visualization : Continuous
            Visualization visu2 = new Visualization();
            Resource resource2 = new Resource("NoTemp",Consts.TEMP_NEG_SENML, Format.SenML);
            visu2.addConcern(Concern.Discrete);
            visu2.addConcern(Concern.Extremum);
            visu2.addResource(resource2);

            // #3 visualization : Continuous
            Visualization visu3 = new Visualization();
            Resource resource3 = new Resource("Speed",Consts.SPEED_SENML, Format.SenML);
            visu3.addConcern(Concern.Extremum);
            visu3.addConcern(Concern.Discrete);
            visu3.addResource(resource3);

            Visualization visuLine = Visualization.Fusion(Visualization.Fusion(visu1, visu2), visu3);
            dashboard.addVisualization(visuLine);



        // Second simple visu //

            // #4 visualization : Proportion
            Visualization visu4 = new Visualization();
            Resource resource4 = new Resource("Proportion",Consts.CATEGORIZED_STACKED, new Column("range","range"), new Column("volume","scalar"), Format.Stacked);
            visu4.addResource(resource4);
            visu4.addConcern(Concern.Proportion);

            dashboard.addVisualization(visu4);



        // First composite visu //

            // #5 visualization : Continuous
            Visualization visu5 = new Visualization();
            Resource resource5 = new Resource("Light",Consts.Raw_LIGHT_SMARTCAMPUS, Format.SmartCampus);
            visu5.addResource(resource5);
            visu5.addConcern(Concern.Continuous);

            // #6 visualization : Continuous
            Visualization visu6 = new Visualization();
            Resource resource6 = new Resource("Temp442",Consts.Raw_TEMP_SMARTCAMPUS, Format.SmartCampus);
            visu6.addResource(resource6);
            visu6.addConcern(Concern.Continuous);

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
        String code = codeGeneration(dashboard);
        //Creation of the /product folder if it doesn't exist already
        FileOperation.setUpFolder(Consts.GENERATED_TARGET_FOLDER);
        //store the resulting visualization in a file named after the used concern
        FileOperation.fillFileFromObject(code, Consts.RUNTIME_FOLDER+Consts.GENERATED_TARGET_FOLDER + "MultipleLeft.html");
    }
}
