package metaclasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan Logre on 02/07/2014.
 */
public class Dashboard {
    private List<Visualization> visualizationList;

    public Dashboard(){
        this.visualizationList = new ArrayList<>();
    }

    public Dashboard(List<Visualization> visualizationList){
        this.visualizationList=visualizationList;
    }

    public void addVisualization(Visualization visualization){
        if(!this.visualizationList.contains(visualization)){
            this.visualizationList.add(visualization);
        }
    }

    public List<Visualization> getVisualizationList(){
        return this.visualizationList;
    }
}
