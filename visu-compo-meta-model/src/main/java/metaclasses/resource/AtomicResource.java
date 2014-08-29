package metaclasses.resource;

import exception.VisitorException;
import metaclasses.Format;
import model.exploitation.VisitorTemplate.IGenerativeVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 27/08/2014.
 */
public class AtomicResource extends Resource {

    private Element key;
    private List<Element> values;
    private Format format;

    public AtomicResource(String semantic, String name, Arity arity, String url, Format format, Element key, Element... values) {
        super(semantic, name, arity, url);
        this.format=format;
        this.key=key;
        this.values= new ArrayList<>();
        for(Element e : values)
            this.values.add(e);
    }

    @Override
    public Element getKey() {
        return key;
    }

    @Override
    public Format getFormat() {
        return format;
    }

    public List<Element> getValues() {
        return values;
    }

    @Override
    public void accept(IGenerativeVisitor gv) throws VisitorException {
        gv.visit(this);
    }
}
