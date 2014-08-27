package metaclasses.concern;

import java.util.HashMap;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
public class Concern {


    // Constructors //
    protected Concern(){
        this.params = new HashMap<>();
    }

    protected Concern(String param, Object value ){
        super();
        this.params.put(param,value);
    }

    // Attributes //
    protected HashMap<String,Object> params;


    // Get & Set //
    public HashMap<String, Object> getParams() {
        return params;
    }


    // Others //
    @Override
    public String toString(){
        return this.getClass().getSimpleName();
    }
}
