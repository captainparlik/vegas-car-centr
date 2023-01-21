package com.captainparlik.exceptions;

public class IllegalBookingException extends RuntimeException {

    public IllegalBookingException(ErrorRegistry errorRegistry) {
        super(errorRegistry.getUnLocalizedMessage());
    }

}
