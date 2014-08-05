package metaclasses.concern;

/**
 * Created by Ivan Logre on 23/06/2014.
 */
public class Concern {

    // My static instances //
     static final Concern Discrete = new Concern("Discrete");
     static final Concern Continuous = new Concern("Continuous");
     static final Concern Extremum = new Concern("Extremum");
     static final Concern Proportion = new Concern("Proportion");

    // Constructors //
    private Concern(String name){
        this.name =name;
    }

    Concern(String name, String param ){
        new Parameterized(name,param);
    }

    // Inner subclass in case the instance need a parameter //
    private class Parameterized extends Concern{

        private String parameter;

        Parameterized(String name, String parameter){
            super(name);
            this.parameter = parameter;
        }

        public String getParameter() {
            return parameter;
        }

        public void setParameter(String parameter) {
            this.parameter = parameter;
        }
    }

    // Attributes //
    private String name;

    // Get & Set //
    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    // Others //
    @Override
    public String toString(){
        return this.getName();
    }
}
