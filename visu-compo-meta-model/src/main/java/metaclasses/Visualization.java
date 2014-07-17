package metaclasses;

import utils.NameCorrectness;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
public class Visualization {
    private List<Data> data;
    private List<Concern> concerns;
    private String name;
    private String widgetName;    //TODO check the existence
    private String libraryName;   //TODO check the existence

    public Visualization(){
        this.name = "v"+ NameCorrectness.format(randomUUID().toString());
        this.concerns = new ArrayList<>();
        this.data = new ArrayList<>();
    }

    public Visualization(Data data){
        this();
        this.data.add(data);
    }

    public Visualization(Data data, Concern concerns){
        this();
        this.addConcern(concerns);
        this.data.add(data);
    }

    public Visualization(List<Data> data, List<Concern> concerns){
        this();
        this.concerns.addAll(concerns);
        this.data.addAll(data);
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

    public List<Data> getDataSets() {
        return data;
    }

    public void addData(Data data){
        if(!this.data.contains(data))
            this.data.add(data);
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

    public static Visualization Fusion(Visualization visu1, Visualization visu2) {
        List<Data> data = visu1.getDataSets();
        for(Data d :visu2.getDataSets())
            if(!data.contains(d))
                data.add(d);
        List<Concern> concerns = visu1.getConcerns();
        for(Concern c :visu2.getConcerns())
            if(!concerns.contains(c))
                concerns.add(c);
        return new Visualization(data,concerns);
    }
}
