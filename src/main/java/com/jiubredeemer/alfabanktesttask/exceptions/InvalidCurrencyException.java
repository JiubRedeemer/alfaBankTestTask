package com.jiubredeemer.alfabanktesttask.exceptions;

public class InvalidCurrencyException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid currency!";
    }
}
