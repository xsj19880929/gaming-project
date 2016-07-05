package com.ygccw.wechat.common.sys.exception;

import core.framework.exception.Ignore;

/**
 * @author chi
 */
@Ignore
public class UserAuthenticationException extends RuntimeException {
    public UserAuthenticationException(String message) {
        super(message);
    }

    public UserAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
