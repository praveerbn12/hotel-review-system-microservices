package com.user.service.UserService.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Date;

@Service
public class JwtService {
    @Autowired
    private  JwtKeyUtil jwtKeyUtil;

    private static final long EXPIRY_MS = 1000 * 60 * 60;

    public  String generateToken(Integer userId, String role) throws  Exception{
        PrivateKey privateKey= jwtKeyUtil.loadPrivateKey();
        Date now = new Date();
        return Jwts.builder().subject(String.valueOf(userId))
                .claim("role",role)
                .issuedAt(now)
                .expiration(new Date(now.getTime()+EXPIRY_MS))
                .signWith(privateKey)
                .compact();
    }
}
