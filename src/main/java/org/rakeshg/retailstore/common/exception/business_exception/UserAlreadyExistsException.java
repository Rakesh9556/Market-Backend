package org.rakeshg.retailstore.common.exception.business_exception;

import org.rakeshg.retailstore.common.exception.ResourceAlreadyExistException;

public class UserAlreadyExistsException extends ResourceAlreadyExistException {
    public UserAlreadyExistsException() {
        super("User already exists");
    }
}
