package metaclasses;

import utils.NameCorrectness;

import static java.util.UUID.randomUUID;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
public class Visualization {
    private Data data;
    private Concern concern;
    private String name;
    private String widgetName;
    //TODO check the existence

    public Visualization(){
        this.name = "v"+ NameCorrectness.format(randomUUID().toString());
    }

    public Visualization(metaclasses.Data data){
        this();
        this.data=data;
    }

    public Visualization(metaclasses.Data data, metaclasses.Concern concern){
        this();
        this.concern=concern;
        this.data=data;
    }

    public Concern getConcern() {
        return concern;
    }

    public Data getData() {
        return data;
    }

    public void addData(Data data){this.data=data;}

    public void addConcern(Concern concern){
        this.concern=concern;
    }

    public String getWidgetName() {return widgetName;}

    public void setWidgetName(String widgetName) {
        this.widgetName = widgetName;
    }

    public String getName() {
        return name;
    }
}
