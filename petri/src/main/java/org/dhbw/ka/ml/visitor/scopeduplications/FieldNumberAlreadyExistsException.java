package org.dhbw.ka.ml.visitor.scopeduplications;

public class FieldNumberAlreadyExistsException extends RuntimeException {

    public FieldNumberAlreadyExistsException(String message) {
        super(message);
    }

    public FieldNumberAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldNumberAlreadyExistsException(Throwable cause) {
        super(cause);
    }

}
