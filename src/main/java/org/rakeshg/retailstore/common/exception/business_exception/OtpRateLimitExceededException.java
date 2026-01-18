package org.rakeshg.retailstore.common.exception.business_exception;

import org.rakeshg.retailstore.common.exception.BusinessException;

public class OtpRateLimitExceededException extends BusinessException {
    public OtpRateLimitExceededException() {
        super("Too many OTP requests. Please try again later.");
    }
}
