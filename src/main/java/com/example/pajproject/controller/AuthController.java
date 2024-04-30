package com.example.pajproject.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.UUID;

public class AuthController {
    private static final String ISSUER = "paj_project";
    private static final Algorithm algorithm = Algorithm.HMAC256("42x0Ok1h1$K1gd>.ZcC9L*V4");
    private static final JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer(ISSUER)
            .build();

    public static Long getIdClaim(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim("id").asLong();
        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static String createJWT(Long id, long ttlMillis) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withClaim("id", id)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + ttlMillis))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    public static boolean validateToken(String token){
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
