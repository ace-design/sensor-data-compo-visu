package metaclasses;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
public class Data {
    private final String url;
    private final Format format;
    private Column index;
    private Column serie;

    public Data(String url,Format format){
        this.url=url;
        this.format = format;
        fillIndexSerieFromFormat();
    }

    public Data(String url,Column index,Column serie,Format format){
        this.url=url;
        this.index = index;
        this.serie = serie;
        this.format = format;
    }
    public String getUrl() {
        return url;
    }

    public Format getFormat() {
        return format;
    }


    public Column getIndex() {
        return index;
    }

    public Column getSerie() {
        return serie;
    }

    private void fillIndexSerieFromFormat() {
        switch(format){
            case SenML:
                index = new Column("t","timestamp");
                serie = new Column("v","scalar");
                break;
            case SmartCampus:
                index = new Column("date","timestamp");
                serie = new Column("value","scalar");
                break;
        }
    }

}
