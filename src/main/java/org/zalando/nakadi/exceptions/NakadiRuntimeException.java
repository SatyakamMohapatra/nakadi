package org.zalando.nakadi.exceptions;

/* This exception is meant to be caught and unwrap the real excpetion */
public class NakadiRuntimeException extends RuntimeException {

    private final Exception exception;

    public NakadiRuntimeException(final Exception exception) {
        super(exception);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

}
