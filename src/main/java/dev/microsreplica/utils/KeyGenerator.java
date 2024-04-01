package dev.microsreplica.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyGenerator {
    public static KeyPair generateRSAKey() {
        KeyPair keyPair;

        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            keyPair = keyGen.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException();
        }

        return keyPair;
    }
}
