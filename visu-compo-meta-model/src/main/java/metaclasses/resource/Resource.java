package metaclasses.resource;

import exception.VisitorException;
import metaclasses.Format;
import metaclasses.Visualization;
import metaclasses.concern.what.WhatQualifier;
import model.exploitation.VisitorTemplate.Generable;
import model.exploitation.VisitorTemplate.IGenerativeVisitor;
import utils.NameCorrectness;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
public abstract class Resource implements Generable {
    private final String url;
    private final String semantic;
    protected String name;
    private Arity arity;
    private List<WhatQualifier> concerns;
    protected Visualization visualization;

    public Resource(String semantic, String name, Arity arity, String url) {
        this.semantic = semantic;
        this.name = name+NameCorrectness.format(randomUUID().toString());
        this.arity = arity;
        this.url = url;
        this.concerns = new ArrayList<>();
    }

    public void addConcern(WhatQualifier q){
        if(!this.concerns.contains(q))
            this.concerns.add(q);
    }

    public abstract Element getKey();

    public List<WhatQualifier> getConcerns() {
        return concerns;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Arity getArity() {
        return arity;
    }

    public Visualization getVisualization() {
        return visualization;
    }

    public void setVisualization(Visualization visualization) {
        this.visualization = visualization;
    }

    abstract public Format getFormat();
}
