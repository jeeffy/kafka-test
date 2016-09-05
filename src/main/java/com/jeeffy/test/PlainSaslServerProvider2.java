package com.jeeffy.test;

import java.security.Provider;
import java.security.Security;

import com.jeeffy.test.PlainSaslServer2.PlainSaslServerFactory2;

public class PlainSaslServerProvider2 extends Provider {

    private static final long serialVersionUID = 1L;

    protected PlainSaslServerProvider2() {
        super("Simple SASL/PLAIN Server Provider", 1.0, "Simple SASL/PLAIN Server Provider for Kafka");
        super.put("SaslServerFactory." + PlainSaslServer2.PLAIN_MECHANISM, PlainSaslServerFactory2.class.getName());
    }

    public static void initialize() {
        Security.addProvider(new PlainSaslServerProvider2());
        System.out.println("---------PlainSaslServerProvider2 initialize");
    }
}
