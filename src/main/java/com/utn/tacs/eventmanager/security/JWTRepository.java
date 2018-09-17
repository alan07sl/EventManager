package com.utn.tacs.eventmanager.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.utn.tacs.eventmanager.security.SecurityConstants.EXPIRATION_TIME;
import static com.utn.tacs.eventmanager.security.SecurityConstants.SECRET;

public class JWTRepository {
    public static final JWTRepository instance = new JWTRepository();
    private List<String> tokens = new ArrayList<>();

    private JWTRepository() {
    }

    public static JWTRepository getInstance() {
        return instance;
    }

    public void addToken(String token) {
        this.tokens.add(token);
    }

    public void removeToken(String token) {
        this.tokens.remove(token);
    }

    public boolean hasToken(String token) {
        return this.tokens.contains(token);
    }

    public String create(String username) {
        String token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        addToken(token);
        return token;
    }

    public String decode(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();
    }
}
