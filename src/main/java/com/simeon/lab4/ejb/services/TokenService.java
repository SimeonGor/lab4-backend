package com.simeon.lab4.ejb.services;


import com.simeon.lab4.ejb.expetions.TokenExpiredException;
import com.simeon.lab4.ejb.expetions.TokenIsInvalidException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.ejb.Stateless;

import javax.crypto.SecretKey;
import java.util.Date;

@Stateless
public class TokenService {
    private static final SecretKey secretKey = Keys.hmacShaKeyFor("rtVogb87HN5ydZ6OVgTXaDVbtEy6HikQj2JiVGL2pmc3S".getBytes());

    public String generateToken(String username, Date expiration) {
        return Jwts.builder()
                .issuer(username)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public String validateToken(String token) throws TokenIsInvalidException, TokenExpiredException {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            if (claims.getExpiration().before(new java.util.Date())) {
                throw new TokenExpiredException();
            }

            return claims.getIssuer();
        }
        catch (JwtException e) {
            throw new TokenIsInvalidException();
        }
    }

}
