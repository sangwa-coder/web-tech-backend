package com.agrillnovate.System.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    private static final String SECRET_KEY = "chDq0DhhpSAz3A2a5HDCUpylSjSoumqj/vT0ZJjId+A="; // Replace with your actual secret key

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        String jwtToken = extractJwtFromHeader(message);
        if (jwtToken != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                    .parseClaimsJws(jwtToken)
                    .getBody();

            String role = claims.get("role", String.class);
            GrantedAuthority authority = new SimpleGrantedAuthority(role);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(),
                    null,
                    Collections.singleton(authority)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        return message;
    }

    private String extractJwtFromHeader(Message<?> message) {
        if (message.getHeaders().containsKey("Authorization")) {
            String authorizationHeader = (String) message.getHeaders().get("Authorization");
            if (authorizationHeader.startsWith("Bearer ")) {
                return authorizationHeader.substring(7);
            }
        }
        return null;
    }
}
