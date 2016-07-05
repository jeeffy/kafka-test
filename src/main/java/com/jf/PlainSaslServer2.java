package com.jf;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.security.auth.callback.CallbackHandler;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslException;
import javax.security.sasl.SaslServer;
import javax.security.sasl.SaslServerFactory;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

public class PlainSaslServer2 implements SaslServer {

    public static final String PLAIN_MECHANISM = "PLAIN";
    //private static final String JAAS_USER_PREFIX = "user_";

    private boolean complete;
    private String authorizationID;

    public PlainSaslServer2(CallbackHandler callbackHandler) {
    }

    
    public byte[] evaluateResponse(byte[] response) throws SaslException {
        /*
         * Message format (from https://tools.ietf.org/html/rfc4616):
         *
         * message   = [authzid] UTF8NUL authcid UTF8NUL passwd
         * authcid   = 1*SAFE ; MUST accept up to 255 octets
         * authzid   = 1*SAFE ; MUST accept up to 255 octets
         * passwd    = 1*SAFE ; MUST accept up to 255 octets
         * UTF8NUL   = %x00 ; UTF-8 encoded NUL character
         *
         * SAFE      = UTF1 / UTF2 / UTF3 / UTF4
         *                ;; any UTF-8 encoded Unicode character except NUL
         */

        String[] tokens;
        try {
            tokens = new String(response, "UTF-8").split("\u0000");
        } catch (UnsupportedEncodingException e) {
            throw new SaslException("UTF-8 encoding not supported", e);
        }
        if (tokens.length != 3)
            throw new SaslException("Invalid SASL/PLAIN response: expected 3 tokens, got " + tokens.length);
        authorizationID = tokens[0];
        String username = tokens[1];
        String password = tokens[2];

        if (username.isEmpty()) {
            throw new SaslException("Authentication failed: username not specified");
        }
        if (password.isEmpty()) {
            throw new SaslException("Authentication failed: password not specified");
        }
        if (authorizationID.isEmpty())
            authorizationID = username;

        try {
            //String expectedPassword = JaasUtils.jaasConfig(LoginType.SERVER.contextName(), JAAS_USER_PREFIX + username);
        	String expectedPassword = getPassword(username);
        	if (!password.equals(expectedPassword)) {
                throw new SaslException("Authentication failed: Invalid username or password");
            }
        } catch (IOException e) {
            throw new SaslException("Authentication failed: Invalid JAAS configuration", e);
        }
        complete = true;
        return new byte[0];
    }

    
    public String getAuthorizationID() {
        if (!complete)
            throw new IllegalStateException("Authentication exchange has not completed");
        return authorizationID;
    }

    
    public String getMechanismName() {
        return PLAIN_MECHANISM;
    }

    
    public Object getNegotiatedProperty(String propName) {
        if (!complete)
            throw new IllegalStateException("Authentication exchange has not completed");
        return null;
    }

    
    public boolean isComplete() {
        return complete;
    }

    
    public byte[] unwrap(byte[] incoming, int offset, int len) throws SaslException {
        if (!complete)
            throw new IllegalStateException("Authentication exchange has not completed");
        return Arrays.copyOfRange(incoming, offset, offset + len);
    }

    
    public byte[] wrap(byte[] outgoing, int offset, int len) throws SaslException {
        if (!complete)
            throw new IllegalStateException("Authentication exchange has not completed");
        return Arrays.copyOfRange(outgoing, offset, offset + len);
    }

    
    public void dispose() throws SaslException {
    }

    public static class PlainSaslServerFactory2 implements SaslServerFactory {

        
        public SaslServer createSaslServer(String mechanism, String protocol, String serverName, Map<String, ?> props, CallbackHandler cbh)
            throws SaslException {

            if (!PLAIN_MECHANISM.equals(mechanism)) {
                throw new SaslException(String.format("Mechanism \'%s\' is not supported. Only PLAIN is supported.", mechanism));
            }
            return new PlainSaslServer2(cbh);
        }

        
        public String[] getMechanismNames(Map<String, ?> props) {
            String noPlainText = (String) props.get(Sasl.POLICY_NOPLAINTEXT);
            if ("true".equals(noPlainText))
                return new String[]{};
            else
                return new String[]{PLAIN_MECHANISM};
        }
    }
    
    private String getPassword(String username){
    	ZkClient zkClient = new ZkClient("zk:2181",5000,5000,new ZkSerializer(){

    		public byte[] serialize(Object data) throws ZkMarshallingError {
    	        return data.toString().getBytes();
    	    }

    	    public String deserialize(byte[] bytes) throws ZkMarshallingError {
    	        return new String(bytes);
    	    }});
    	String password = null;
    	List<String> list = zkClient.getChildren("/unistacks/kafka-sasl");
    	if(list.contains(username)){
    		password = zkClient.readData("/unistacks/kafka-sasl/" + username);
    	}
    	
    	return password;
    }
    
    public static void main(String[] args) {
		System.out.println(new PlainSaslServer2(null).getPassword("alice"));
	}
}
