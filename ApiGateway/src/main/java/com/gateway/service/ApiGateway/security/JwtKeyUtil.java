package com.gateway.service.ApiGateway.security;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class JwtKeyUtil {

    public PublicKey loadPublicKey() throws Exception {
        var bytes = new ClassPathResource("keys/public.pem")
                .getInputStream().readAllBytes();
        String pem = new String(bytes, StandardCharsets.UTF_8);

        String base64 = pem
                .replace("-----BEGIN PUBLIC KEY-----", "")   // PUBLIC, not PRIVATE
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(base64);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);   // X.509 for public keys
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }
}
