package metaclasses;

import utils.NameCorrectness;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
public class Visualization {
    private Data data;
    private List<Concern> concerns;
    private String name;
    private String widgetName;    //TODO check the existence
    private String libraryName;   //TODO check the existence

    public Visualization(){
        this.name = "v"+ NameCorrectness.format(randomUUID().toString());
        this.concerns =new ArrayList<>();
    }

    public Visualization(metaclasses.Data data){
        this();
        this.data=data;
    }

    public Visualization(metaclasses.Data data, metaclasses.Concern concerns){
        this();
        this.addConcern(concerns);
        this.data=data;
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

    public Data getData() {
        return data;
    }

    public void addData(Data data){this.data=data;}

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

}
