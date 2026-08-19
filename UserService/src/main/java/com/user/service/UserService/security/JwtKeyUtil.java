package com.user.service.UserService.security;


import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
public class JwtKeyUtil {

    public PrivateKey loadPrivateKey() throws  Exception{
        var bytes= new ClassPathResource("keys/private_pkcs8.pem").getInputStream().readAllBytes();
        String pem = new String(bytes, StandardCharsets.UTF_8);
        String base64 = pem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded= Base64.getDecoder().decode(base64);
        PKCS8EncodedKeySpec spec= new PKCS8EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);

    }
}
