package org.rakeshg.retailstore.common.exception.business_exception;

import org.rakeshg.retailstore.common.exception.BusinessException;
import org.rakeshg.retailstore.common.exception.ResourceNotFoundException;

public class OtpNotFoundException extends ResourceNotFoundException {
    public OtpNotFoundException() {
        super("OTP not found");
    }
}
