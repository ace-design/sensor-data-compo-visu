package metaclasses.resource;

import metaclasses.Visualization;

/**
 * Created by ivan on 27/08/2014.
 */
public class Binding {

    private String name;
    private CompositeResource compose;
    private Resource reference;

    public Binding(String name, Resource reference) {
        this.name = name;
        this.reference = reference;
    }

    public void addComposite(CompositeResource cr){
        this.compose = cr;
    }

    public String getName() {
        return name;
    }

    public CompositeResource getCompose() {
        return compose;
    }

    public Resource getReference() {
        return reference;
    }

    public void propagateVisualization(Visualization visualization) {
        reference.setVisualization(visualization);
    }
}
