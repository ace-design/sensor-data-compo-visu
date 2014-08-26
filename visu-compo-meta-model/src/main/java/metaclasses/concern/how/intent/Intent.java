package metaclasses.concern.how.intent;

import metaclasses.concern.how.HowQualifier;

/**
 * Created by ivan on 26/08/2014.
 */
public abstract class Intent extends HowQualifier {
    Intent() {
        super();
    }
    Intent(String param, Object value) {
        super(param, value);
    }
}
