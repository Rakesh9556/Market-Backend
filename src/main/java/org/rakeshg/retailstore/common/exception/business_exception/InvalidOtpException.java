package org.rakeshg.retailstore.common.exception.business_exception;

import org.rakeshg.retailstore.common.exception.BusinessException;

public class InvalidOtpException extends BusinessException {
    public InvalidOtpException() {
        super("Invalid OTP");
    }
}
