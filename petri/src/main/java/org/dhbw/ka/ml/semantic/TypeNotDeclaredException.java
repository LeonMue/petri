package org.dhbw.ka.ml.semantic;

public class TypeNotDeclaredException extends RuntimeException {

    public TypeNotDeclaredException(String message) {
        super(message);
    }

    public TypeNotDeclaredException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeNotDeclaredException(Throwable cause) {
        super(cause);
    }
}
