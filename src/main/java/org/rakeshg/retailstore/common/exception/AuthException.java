package org.rakeshg.retailstore.common.exception;

public class AuthException extends BusinessException {
    public AuthException(String message, String code) {
        super(message, code);
    }
}
