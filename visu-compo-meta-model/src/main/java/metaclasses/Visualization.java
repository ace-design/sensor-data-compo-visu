package metaclasses;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
public class Visualization {
    private Data data;
    private Concern concern;

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
}
