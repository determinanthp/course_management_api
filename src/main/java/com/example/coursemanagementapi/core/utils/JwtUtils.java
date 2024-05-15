package com.example.coursemanagementapi.core.utils;

import com.example.coursemanagementapi.persistence.entities.User;
import com.example.coursemanagementapi.persistence.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final UserRepository userRepository;
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    @Value("${jwt.expiration}")
    private int expiration;

    public String getJwtFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null) {
            throw new AuthenticationCredentialsNotFoundException("Unauthorized");
        }

        if (authorizationHeader.startsWith("Bearer")) {
            String jwtToken = authorizationHeader.substring(7).trim();
            return jwtToken;
        }
        throw new RuntimeException("Not Bearer token");
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateJwtToken(String authToken, UserDetails userDetails) {
        String username = extractUserName(authToken);
        return (username.equals(userDetails.getUsername())) & !isTokenExpired(authToken);
    }

    public String generateToken(String username) {
        User user;
        if (username.contains("@")) {
            user = userRepository.findByEmail(username).orElseThrow(() -> new NoResultException("Invalid username or password"));
        } else {
            user = userRepository.findByUsername(username).orElseThrow(() -> new NoResultException("Invalid username or password"));
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", "Bearer");
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("firstName", user.getFirstname());
        claims.put("lastName", user.getLastname());
        claims.put("lastLogin", user.getLastActiveTime());

        return buildToken(claims, user, expiration);
    }

    public String generateRefreshToken(String username) {
        User user;
        if (username.contains("@")) {
            user = userRepository.findByEmail(username).orElseThrow(() -> new NoResultException("Invalid username or password"));
        } else {
            user = userRepository.findByUsername(username).orElseThrow(() -> new NoResultException("Invalid username or password"));
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("token_type", "Bearer");
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("firstName", user.getFirstname());
        claims.put("lastName", user.getLastname());
        claims.put("lastLogin", user.getLastActiveTime());

        return buildToken(claims, user, refreshExpiration);
    }


    private String buildToken(Map<String, Object> claims, User userDetails, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] key = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(key);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaim(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaim(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public Date getExpirationTime(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}

