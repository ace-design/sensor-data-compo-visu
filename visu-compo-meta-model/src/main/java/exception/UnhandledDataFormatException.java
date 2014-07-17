package exception;

/**
 * Created by ivan on 08/07/2014.
 */
public class UnhandledDataFormatException extends VisitorException {

    public UnhandledDataFormatException(){
        super();
    };

    public UnhandledDataFormatException(String s){
        super(s);
    };
}
