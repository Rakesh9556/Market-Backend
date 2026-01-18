package org.rakeshg.retailstore.common.exception.business_exception;

import org.rakeshg.retailstore.common.exception.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException() {
        super("User not found");
    }
}
