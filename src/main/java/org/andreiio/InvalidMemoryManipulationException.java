package org.andreiio;

public class InvalidMemoryManipulationException extends RuntimeException{
    public InvalidMemoryManipulationException() {
    }

    public InvalidMemoryManipulationException(String message) {
        super(message);
    }

    public InvalidMemoryManipulationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMemoryManipulationException(Throwable cause) {
        super(cause);
    }

    public InvalidMemoryManipulationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
