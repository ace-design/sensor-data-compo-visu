package metaclasses.concern;

import metaclasses.concern.how.datajournalism.Distribution;
import metaclasses.concern.how.datajournalism.Location;
import metaclasses.concern.how.datajournalism.Proportion;
import metaclasses.concern.how.intent.Extremum;
import metaclasses.concern.how.intent.Variations;
import metaclasses.concern.what.graphical.Icon;
import metaclasses.concern.what.property.Continuous;
import metaclasses.concern.what.property.Discrete;

/**
 * Created by ivan on 05/08/2014.
 */
public class ConcernFactory {

    // Concerns for Visualization //
    public Variations Variations(){return new Variations();};
    public Distribution Distribution(){return new Distribution();};
    public Extremum Extremum(){return new Extremum();};
    public Proportion Proportion(){return new Proportion();};
    public Location Location(String descFile){return new Location("LocationDescription",descFile);};

    // Concerns for Resources //
    public Continuous Continuous(){return new Continuous();};
    public Discrete Discrete(){return new Discrete();};
    public Icon Icon(String pngPath){return new Icon("IconPath",pngPath);};

}
