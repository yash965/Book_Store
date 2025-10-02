package com.Yash.Book_Store.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    private String createToken(Map<String, Object> claims, String username)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((System.currentTimeMillis())+(1000*60*60*10)))    // 10 hrs.
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public String generateToken(String username)
    {
        log.info("Logged in Successfully - {}", username);

        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    public Claims extractAllClaims(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // For extracting a particular claim/payload.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token)
    {
        return extractClaim(token, Claims -> Claims.getSubject());
    }

    public Date extractExpiration(String token)
    {
        return extractClaim(token, Claims -> Claims.getExpiration());
    }

    // Here we have used the wrapper class Boolean (it can contain null also).
    public Boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails)
    {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Key getSigningKey()
    {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
}
