package exception;

/**
 * Created by ivan on 02/09/2014.
 */
public class UnableToMoveResources extends VisitorException {

    public UnableToMoveResources(){
        super();
    };

    public UnableToMoveResources(String s){
        super(s);
    };
}
