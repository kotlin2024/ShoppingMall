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
        // secret 값이 제대로 주입되었는지 확인
        System.out.println("Issuer: " + jwtProperties.getIssuer());  // issuer 값 확인
        System.out.println("Secret: " + jwtProperties.getSecret());  // secret 값 확인

        if (jwtProperties.getSecret() == null || jwtProperties.getSecret().isEmpty()) {
            throw new IllegalArgumentException("JWT secret cannot be null or empty");
        }
        this.key = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
    }

    private String generateToken(String subject, int expirationTime, String memberRole) {
        Claims claims = Jwts.claims().build();
        Instant now = Instant.now();
        claims.put("memberRole", memberRole);

        return Jwts.builder()
                .subject(subject)
                .issuer(jwtProperties.getIssuer())
                .issuedAt(Date.from(now))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .claims(claims)
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