package com.ygccw.msite.mobile.common.service;

import core.framework.web.site.session.SessionContext;
import core.framework.web.site.session.SessionKey;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author soldier
 */
@Service
public class SessionService {
    @Inject
    private SessionContext sessionContext;

    public void saveSession(SessionKey<String> key, String value) {
        sessionContext.set(key, value);
    }

    public String findSession(SessionKey<String> key) {
        return sessionContext.get(key);
    }
}
