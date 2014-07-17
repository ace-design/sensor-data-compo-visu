package metaclasses;

import exception.VisitorException;
import model.exploitation.VisitorTemplate.Generable;
import model.exploitation.VisitorTemplate.IGenerativeVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan Logre on 02/07/2014.
 */
public class Dashboard implements Generable {
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
            visualization.setDashboard(this);
        }
    }

    public List<Visualization> getVisualizationList(){
        return this.visualizationList;
    }

    @Override
    public void accept(IGenerativeVisitor gv) throws VisitorException {
        gv.visit(this);
    }
}
