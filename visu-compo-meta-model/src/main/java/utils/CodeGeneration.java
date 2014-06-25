package utils;

import metaclasses.Concern;
import metaclasses.Visualization;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

import java.nio.file.Paths;

/**
 * Created by Ivan Logre on 25/06/2014.
 */
public class CodeGeneration {

    /*
     * This function get the string template pattern and fill it with relevant information from the given visu-compo model
     */
    public static String codeGeneration(Visualization visualization){

        //Extraction of the dataset from the data instance of the model
        String dataSource = visualization.getData().getUrl();
        String dataset_JSON = "";
        //From the url, we extract the raw String of flat data formatted on JSON (senML)
        dataset_JSON = FileOperation.getStringFromFile(dataSource);
        // Then we convert it in a format understandable by AmChart
        String dataset_AmChart = DataConverter.convertSenML2AmChartFormat(dataset_JSON);

        // We grab the group of string pattern
        STGroup group = new STGroupDir(Paths.get("").toAbsolutePath().toString()+"/visu-compo-meta-model/src/main/resources/stringtemplates/",'$', '$');

        //Framework
        ST myPage = group.getInstanceOf("html");

        //Data
        ST st_data = group.getInstanceOf("data");
        st_data.add("dataname", "datanameTest");
        st_data.add("datavalues", dataset_AmChart);

        //Chart
        ST st_chart = group.getInstanceOf("chart");
        st_chart.add("chartname", "mychart");
        st_chart.add("dataname", "datanameTest");
        st_chart.add("categoryname", "t");

        //Chart Body
        ST st_chartBody = group.getInstanceOf("chartbody");
        st_chartBody.add("chartname", "mychart");
        st_chartBody.add("widthpercent", 100);

        //Graph
        ST st_graph = group.getInstanceOf("graph");
        st_graph.add("chartname", "mychart");
        st_graph.add("graphname", "mygraph");
        st_graph.add("seriename", "v");

        /*
        For now, the condition is hard coded (continuous -> Line, discrete -> Column)
        TODO it has to be replace by Feature Model reduction
        */
        ST st_graphspe = null;
        if(visualization.getConcern().equals(Concern.continuous)){ //if we want a LineChart
            //Line
            st_graphspe = group.getInstanceOf("graphLine");
        }
        else if (visualization.getConcern().equals(Concern.discrete)){ //if we want a ColumnChart
            //Column
            st_graphspe = group.getInstanceOf("graphColumn");
        }
        st_graphspe.add("graphname", "mygraph");
        st_graph.add("graphspe", st_graphspe);
        st_chart.add("graphs",st_graph);

        //Framework end
        myPage.add("data", st_data);
        myPage.add("chartsscript", st_chart);
        myPage.add("chartsbody", st_chartBody);

        return myPage.render();
    }
}
