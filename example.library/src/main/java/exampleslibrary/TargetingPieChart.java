package exampleslibrary;

import constants.Consts;
import exception.BadIDException;
import exception.GetNameOnNonCompleteConfiguration;
import exception.UnhandledDataFormatException;
import metaclasses.*;
import utils.FileOperation;

import java.io.IOException;
import java.nio.file.Paths;

import static model.exploitation.CodeGeneration.codeGeneration;

/**
 * Created by ivan on 08/07/2014.
 */
public class TargetingPieChart {
    public static void main(String[] args) throws IOException, BadIDException, GetNameOnNonCompleteConfiguration, UnhandledDataFormatException {
        //Design the model of the wanted dashboard
        Dashboard dashboard = new Dashboard();
            Visualization visu = new Visualization();
            visu.addData(new Data(Consts.CATEGORIZED_STACKED, new Column("range","range"), new Column("volume","scalar"), Format.Stacked));
            visu.setWidgetName("PieChart");
            visu.setLibraryName("highchart");

        dashboard.addVisualization(visu);

        //Generation of the HTML code from the model
        String code = codeGeneration(dashboard);

        //Creation of the /product folder if it doesn't exist already
        FileOperation.setUpFolder(Consts.GENERATED_TARGET_FOLDER);

        //store the resulting visualization in a file named after the used concern
        FileOperation.fillFileFromObject(code, Paths.get("").toAbsolutePath().toString() + Consts.GENERATED_TARGET_FOLDER + "PieChart.html");

    }
}
