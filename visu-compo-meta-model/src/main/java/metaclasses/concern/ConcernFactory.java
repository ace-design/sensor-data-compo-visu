package metaclasses.concern;

import metaclasses.concern.Concern;

/**
 * Created by ivan on 05/08/2014.
 */
public class ConcernFactory {



    // Instance Factory & Accessor //
    public Concern Discrete(){return Concern.Discrete;};
    public Concern Continuous(){return Concern.Continuous;};
    public Concern Extremum(){return Concern.Extremum;};
    public Concern Proportion(){return Concern.Proportion;};
    public Concern Spatial(String descFile){return new Concern("Spatial",descFile);};
}
