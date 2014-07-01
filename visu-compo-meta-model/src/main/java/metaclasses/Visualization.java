package metaclasses;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
public class Visualization {
    private Data data;
    private Concern concern;
    private String widgetName;
    //TODO check the existence

    public Visualization(){}

    public Visualization(metaclasses.Data data){
        this.data=data;
    }

    public Visualization(metaclasses.Data data, metaclasses.Concern concern){
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

}
