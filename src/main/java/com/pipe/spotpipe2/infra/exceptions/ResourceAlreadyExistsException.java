package com.pipe.spotpipe2.infra.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(Object resource) {
        super(String.format("resource %s already exists", resource));
    }
}
