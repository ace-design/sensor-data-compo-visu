package metaclasses;

/**
 * Created by ivan on 09/07/2014.
 */
public class Column {
    private String name;
    private String type;

    public Column(String name,String type) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
