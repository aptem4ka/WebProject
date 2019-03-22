package com.epam.hotel.exception;

/**
 * An exception that provides information about error in the service layer.
 *
 * @author Artsem Lashuk
 */
public class DAOException extends Exception {

    /**
     * Constructs a new exception with null as its detail message.
     */
    public DAOException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     * @param message specified detail message
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * @param message specified message
     * @param cause specified cause
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message of
     * (cause==null ? null : cause.toString()) (which typically contains the class
     * and detail message of cause).
     * @param cause specified cause
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message, cause,
     * suppression enabled or disabled, and writable stack trace enabled or disabled.
     * @param message specified message
     * @param cause specified cause
     * @param enableSuppression whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    protected DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
