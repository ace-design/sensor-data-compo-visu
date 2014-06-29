package metaclasses;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
public class Data {
    private String url;
    private Format format;
    public Data(String url,Format format){this.url=url;this.format=format;}
    public String getUrl() {return url;}
    public Format getFormat() {return format;}
}
