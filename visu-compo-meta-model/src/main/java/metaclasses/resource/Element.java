package metaclasses.resource;

/**
 * Created by ivan on 27/08/2014.
 */
public class Element {
    private String name;
    private DataType type;

    public Element(String name, DataType type) {
        this.name = name;
        this.type = type;
    }

    public DataType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element element = (Element) o;

        if (!name.equals(element.name)) return false;
        if (type != element.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
