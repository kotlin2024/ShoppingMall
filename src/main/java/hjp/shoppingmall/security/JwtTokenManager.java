package hjp.shoppingmall.security;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenManager {


    private final JwtProperties jwtProperties;
    private byte[] key;

    private final int accessTokenValidity = 60 * 60 * 1000;

    public JwtTokenManager(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @PostConstruct
    public void init() {

        if (jwtProperties.getSecret() == null || jwtProperties.getSecret().isEmpty()) {
            throw new IllegalArgumentException("JWT secret cannot be null or empty");
        }
        this.key = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
    }

    private String generateToken(String subject, int expirationTime, String memberRole) {

        ClaimsBuilder claimsBuilder = Jwts.claims();

        claimsBuilder.subject(subject);
        claimsBuilder.add("memberRole", memberRole);

        Claims claims = claimsBuilder.build();
        Instant now = Instant.now();

        return Jwts.builder()
                .claims(claims)
                .issuer(jwtProperties.getIssuer())
                .issuedAt(Date.from(now))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(Keys.hmacShaKeyFor(key))
                .compact();
    }

    public String generateTokenResponse(Long memberId, String memberRole) {
        return generateToken(memberId.toString(), accessTokenValidity, memberRole);
    }

    public Jws<Claims> validateToken(String token) throws JwtException {
        try {
            JwtParser jwtParser = Jwts.parser() // JwtParserBuilder 객체 반환
                    .verifyWith(Keys.hmacShaKeyFor(key)) // 서명 키 설정
                    .build(); // JwtParser 객체로 빌드

            return jwtParser.parseSignedClaims(token);
        } catch (JwtException e) {
            // handle invalid token, e.g., log or rethrow
            throw new JwtException("Invalid token", e);
        }
    }
}