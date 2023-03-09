package org.dhbw.ka.ml.visitor.scopeduplications;


public class IdentifierAlreadyDeclaredException extends RuntimeException {

    public IdentifierAlreadyDeclaredException(String message) {
        super(message);
    }

    public IdentifierAlreadyDeclaredException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdentifierAlreadyDeclaredException(Throwable cause) {
        super(cause);
    }

}
