package com.pipe.spotpipe2.infra.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Object id) {
        super(String.format("resource with id: %s not found", id));
    }
}
