package exception;

/**
 * Created by ivan on 01/09/2014.
 */
public class IncoherentConcernException extends VisitorException {

    public IncoherentConcernException(){
        super();
    };

    public IncoherentConcernException(String s){
        super(s);
    };
}
