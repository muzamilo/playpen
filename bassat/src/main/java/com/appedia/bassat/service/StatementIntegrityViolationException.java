package com.appedia.bassat.service;

/**
 *
 * @author Muz Omar
 */
public class StatementIntegrityViolationException extends Exception {

    /**
     *
     * @param message
     * @param cause
     */
    public StatementIntegrityViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
