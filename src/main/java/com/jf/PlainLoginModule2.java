package com.jf;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class PlainLoginModule2 implements LoginModule {

    private static final String USERNAME_CONFIG = "username";
    private static final String PASSWORD_CONFIG = "password";

    static {
        PlainSaslServerProvider2.initialize();
    }

    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        String username = (String) options.get(USERNAME_CONFIG);
        if (username != null)
            subject.getPublicCredentials().add(username);
        String password = (String) options.get(PASSWORD_CONFIG);
        if (password != null)
        	subject.getPrivateCredentials().add(password);
        System.out.println("login init----"+subject);
    }

    public boolean login() throws LoginException {
        return true;
    }

    public boolean logout() throws LoginException {
        return true;
    }

    public boolean commit() throws LoginException {
        return true;
    }

    public boolean abort() throws LoginException {
        return false;
    }
}
