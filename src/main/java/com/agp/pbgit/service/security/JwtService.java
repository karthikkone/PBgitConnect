package com.agp.pbgit.service.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class JwtService {
    @Value("jwt.signing_key")
    private String secretKey;

    @Value("jwt.issuer")
    private String issuer;

    @Value("#{T(java.lang.Long).parseLong('${jwt.expire_hours}')}")
    private Long expireHours;

    public String tokenFor(String userprofile) throws IOException {

        if (userprofile != null && userprofile.length() != 0) {
            //oauth token serves as minimal user profile
            GitHub gitHub = GitHub.connectUsingOAuth(userprofile);
            final String subject = gitHub.getMyself().getLogin();

            final Date expiration = Date.from(LocalDateTime.now().plusHours(expireHours).toInstant(ZoneOffset.UTC));
            final Key signingKey = Keys.hmacShaKeyFor(secretKey.getBytes());

            if (subject != null) {
                return Jwts.builder()
                        .setSubject(subject)
                        .setExpiration(expiration)
                        .setIssuer(issuer)
                        .signWith(signingKey)
                        .compact();
            } else {
                throw new IOException("failed to parse OAuth token / minimal user profile");
            }
        }

        return null;
    }
}
