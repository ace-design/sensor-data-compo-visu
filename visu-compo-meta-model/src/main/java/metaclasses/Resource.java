package metaclasses;

import exception.VisitorException;
import model.exploitation.VisitorTemplate.Generable;
import model.exploitation.VisitorTemplate.IGenerativeVisitor;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
public class Resource implements Generable {
    private Visualization visualization;
    private final String url;
    private final Format format;
    private Column index;
    private Column serie;

    public Resource(String url, Format format){
        this.url=url;
        this.format = format;
        fillIndexSerieFromFormat();
    }

    public Resource(String url, Column index, Column serie, Format format){
        this.url=url;
        this.index = index;
        this.serie = serie;
        this.format = format;
    }

    public Visualization getVisualization() {
        return visualization;
    }

    public void setVisualization(Visualization visualization) {
        this.visualization = visualization;
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
                serie = new Column("v","double");
                break;
            case SmartCampus:
                index = new Column("date","timestamp");
                serie = new Column("value","scalar");
                break;
        }
    }


    @Override
    public void accept(IGenerativeVisitor gv) throws VisitorException {
        gv.visit(this);
    }

}
