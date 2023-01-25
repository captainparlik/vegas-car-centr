package com.captainparlik.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorRegistry {

    BOOKING_NOT_FOUND(1, "Booking Not Found"),
    SERVICE_NOT_FOUND(2,"Service Not Found");

    private final int code;
    private final String unLocalizedMessage;
}
