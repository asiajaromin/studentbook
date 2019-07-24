package pl.jcommerce.joannajaromin.studentbook.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class KeyProvider {

    private static SecretKey keyProvider = null;

    public static SecretKey getKey(){
        if (keyProvider == null){
            keyProvider = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        }
        return keyProvider;
    }
}
