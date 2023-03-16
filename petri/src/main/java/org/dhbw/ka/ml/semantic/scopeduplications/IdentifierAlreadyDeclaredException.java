package org.dhbw.ka.ml.semantic.scopeduplications;


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
