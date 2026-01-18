package org.rakeshg.retailstore.common.exception.business_exception;

import org.rakeshg.retailstore.common.exception.BusinessException;

public class ResourceForbiddenException extends BusinessException {
    public ResourceForbiddenException(String message) {
        super(message);
    }
}
