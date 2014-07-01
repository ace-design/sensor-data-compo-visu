package metaclasses;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
public class Data {
    private final String url;
    private final Format format;


    public Data(String url,Format format){
        this.url=url;
        this.format=format;
    }
    public String getUrl() {
        return url;
    }

    public Format getFormat() {
        return format;
    }

}
