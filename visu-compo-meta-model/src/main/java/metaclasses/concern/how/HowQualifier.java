package metaclasses.concern.how;

import metaclasses.concern.Concern;

/**
 * Created by ivan on 26/08/2014.
 */
public abstract class HowQualifier extends Concern {
    protected HowQualifier() {
        super();
    }
    protected HowQualifier(String param, Object value) {
        super(param, value);
    }
}
