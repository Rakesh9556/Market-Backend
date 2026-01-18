package org.rakeshg.retailstore.common.exception.business_exception;

import org.rakeshg.retailstore.common.exception.BusinessException;

public class InactiveUserException extends BusinessException {
    public InactiveUserException() {
        super("User account is inactive");
    }
}
