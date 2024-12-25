package com.SCM.SCM20.Helper;

public class ResourceNotFoundException extends RuntimeException{


    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException() {
        super("Resource not found");
    }
}
