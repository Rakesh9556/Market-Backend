package org.rakeshg.retailstore.common.exception.business_exception;

import org.rakeshg.retailstore.common.exception.ResourceAlreadyExistException;

public class StoreAlreadyExistsException extends ResourceAlreadyExistException {
    public StoreAlreadyExistsException() {
        super("Store already onboarded");
    }
}
