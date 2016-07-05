package com.ygccw.wechat.sys;

import com.ygccw.wechat.common.sys.exception.UserAuthenticationException;
import com.ygccw.wechat.common.sys.exception.UserAuthorizationException;
import core.framework.util.JSONBinder;
import core.framework.web.rest.exception.ErrorResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chi
 */
@ControllerAdvice
public class SecurityErrorControllerAdvice {
    @Inject
    SecuritySettings securitySettings;
    @Inject
    protected ErrorResponseBuilder errorResponseBuilder;

    @ExceptionHandler(UserAuthenticationException.class)
    public void unauthenticated(HttpServletRequest request, HttpServletResponse response, UserAuthenticationException e) throws IOException {
        if (isRestRequest(request)) {
            response.getWriter().write(JSONBinder.toJSON(errorResponseBuilder.createErrorResponse(e)));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } else {
            //TODO: not sure works
            response.sendRedirect(securitySettings.getUnauthenticatedUrl());
        }
    }

    @ExceptionHandler(UserAuthorizationException.class)
    public void unauthorized(HttpServletRequest request, HttpServletResponse response, UserAuthorizationException e) throws IOException {
        if (isRestRequest(request)) {
            response.getWriter().write(JSONBinder.toJSON(errorResponseBuilder.createErrorResponse(e)));
            response.setStatus(HttpStatus.FORBIDDEN.value());
        } else {
            //TODO: not sure works
            response.sendRedirect(securitySettings.getUnauthorizedUrl());
        }
    }


    protected boolean isRestRequest(HttpServletRequest request) {
        return request.getHeader("ACCEPT").contains("application/json");
    }
}
