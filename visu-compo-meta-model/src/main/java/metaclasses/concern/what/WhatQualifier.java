package metaclasses.concern.what;

import metaclasses.concern.Concern;

/**
 * Created by ivan on 26/08/2014.
 */
public abstract class WhatQualifier extends Concern {
    protected WhatQualifier() {
        super();
    }
    protected WhatQualifier(String param, Object value) {
        super(param, value);
    }
}
