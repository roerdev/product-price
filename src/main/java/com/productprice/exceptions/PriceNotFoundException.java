package com.productprice.exceptions;

public class PriceNotFoundException extends Exception {

    public PriceNotFoundException() {
        super();
    }

    public PriceNotFoundException(String message) {
        super(message);
    }

    public PriceNotFoundException(Exception newOriginalException) {
        super(newOriginalException);
    }
}
