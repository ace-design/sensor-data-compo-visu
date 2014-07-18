package metaclasses;

import exception.IncompatibleIndexAxisException;
import exception.VisitorException;
import model.exploitation.VisitorTemplate.Generable;
import model.exploitation.VisitorTemplate.IGenerativeVisitor;
import utils.NameCorrectness;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
public class Visualization implements Generable {
    private Dashboard dashboard;
    private List<Resource> resources;
    private List<Concern> concerns;
    private String name;
    private String widgetName;    //TODO check the existence
    private String libraryName;   //TODO check the existence
    private String indexName;

    public Visualization(){
        this.name = "v"+ NameCorrectness.format(randomUUID().toString());
        this.concerns = new ArrayList<>();
        this.resources = new ArrayList<>();
    }

    public Visualization(Resource resource) throws IncompatibleIndexAxisException {
        this();
        this.addResource(resource);
    }

    public Visualization(Resource resource, Concern concerns) throws IncompatibleIndexAxisException {
        this();
        this.addConcern(concerns);
        this.addResource(resource);
    }

    public Visualization(List<Resource> resource, List<Concern> concerns){
        this();
        this.concerns.addAll(concerns);
        this.resources.addAll(resource);
    }

    public List<Concern> getConcerns() {
        return this.concerns;
    }

    public List<String> getConcernNames() {
        List<String> names = new ArrayList<>();
        for(Concern concern : this.concerns)
            names.add(concern.toString());
        return names;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void addResource(Resource resource) throws IncompatibleIndexAxisException {
        if(!this.resources.contains(resource)) {
            this.resources.add(resource);
            resource.setVisualization(this);
            if(this.indexName==null)
                this.indexName = resource.getIndex().getName();
            else if(!this.indexName.equalsIgnoreCase(resource.getIndex().getName()))
                throw new IncompatibleIndexAxisException("You can't add a ressource with a different kind of index to this visualization.");
        }
    }

    public void addConcern(Concern concern){
        if(!this.concerns.contains(concern))
            this.concerns.add(concern);
    }

    public String getWidgetName() {return widgetName;}

    public void setWidgetName(String widgetName) {
        this.widgetName = widgetName;
    }

    public String getName() {
        return name;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public static Visualization Fusion(Visualization visu1, Visualization visu2) throws IncompatibleIndexAxisException {
        Visualization visualization = new Visualization();
        for(Resource resource : visu1.getResources())
            visualization.addResource(resource);
        for(Resource resource : visu2.getResources())
            visualization.addResource(resource);
        boolean sametype = true;
        Object[] array = visualization.getResources().toArray();
        Resource first = (Resource)array[0];
        String type = first.getIndex().getType();
        for(int i=1;i<array.length;i++) {
            Resource current = (Resource)array[i];
            if (!current.getIndex().getType().equalsIgnoreCase(type))
                throw new IncompatibleIndexAxisException("You can't fusion visualization with different index types !");
        }
        for(Concern concern : visu1.getConcerns())
            visualization.addConcern(concern);
        for(Concern concern : visu2.getConcerns())
            visualization.addConcern(concern);
        return visualization;
    }

    @Override
    public void accept(IGenerativeVisitor gv) throws VisitorException {
        gv.visit(this);
    }

    public String getIndexName() {
        return indexName;
    }
}
