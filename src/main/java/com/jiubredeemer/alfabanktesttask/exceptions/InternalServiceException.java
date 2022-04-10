package com.jiubredeemer.alfabanktesttask.exceptions;

public class InternalServiceException extends Exception {
    @Override
    public String getMessage() {
        return "Internal service error, try again later";
    }
}
