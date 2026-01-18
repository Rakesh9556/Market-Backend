package org.rakeshg.retailstore.common.exception.business_exception;

import org.rakeshg.retailstore.common.exception.BusinessException;

public class OtpExpiredException extends BusinessException {
    public OtpExpiredException() {
        super("OTP has expired");
    }
}
