package com.productprice.exceptions;

public class BrandNotFoundException extends Exception {

    public BrandNotFoundException() {
        super();
    }

    public BrandNotFoundException(String message) {
        super(message);
    }

    public BrandNotFoundException(Exception newOriginalException) {
        super(newOriginalException);
    }
}
