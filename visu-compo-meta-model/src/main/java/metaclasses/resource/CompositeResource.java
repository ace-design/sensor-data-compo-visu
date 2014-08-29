package metaclasses.resource;

import exception.IncompatibleIndexAxisException;
import exception.VisitorException;
import metaclasses.Format;
import metaclasses.Visualization;
import model.exploitation.VisitorTemplate.IGenerativeVisitor;

import java.util.List;

/**
 * Created by ivan on 27/08/2014.
 */
public class CompositeResource extends Resource {

    private List<Binding> composedOf;

    public CompositeResource(String semantic, String name, Arity arity, String url, Binding... bidings) throws IncompatibleIndexAxisException {
        super(semantic, name, arity, url);
        for(Binding b:bidings) {
            composedOf.add(b);
            b.addComposite(this);
        }
    }

    @Override
    public Element getKey(){
        Binding b = (Binding) this.composedOf.toArray()[0];
        return b.getReference().getKey();
    }

    @Override
    public Format getFormat() {
        return Format.Custom;
    }

    public void addBinding(Binding b){
        if(!this.composedOf.contains(b))
            this.composedOf.add(b);
    }

    public List<Binding> getComposedOf() {
        return composedOf;
    }

    @Override
    public void accept(IGenerativeVisitor gv) throws VisitorException {
        gv.visit(this);
    }

    @Override

    public void setVisualization(Visualization visualization) {
        this.visualization = visualization;
        for(Binding b:this.composedOf) {
            b.propagateVisualization(visualization);
        }
    }
}
