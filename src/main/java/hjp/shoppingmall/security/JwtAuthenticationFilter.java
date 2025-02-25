package hjp.shoppingmall.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenManager;

    public JwtAuthenticationFilter(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String token = extractToken(request);
        if (token != null) {

            Jws<Claims> jws = jwtTokenManager.validateToken(token);

            if (jws != null) {
                Claims claims = jws.getPayload();  // Claims 객체 추출
                String memberId = claims.getSubject();
                // memberRole을 JWT claims에서 파싱하여 가져오는 코드 추가
                String memberRole = claims.get("memberRole", String.class);

                UserPrincipal userPrincipal = new UserPrincipal(Long.parseLong(memberId), Collections.singleton(memberRole));
                JwtAuthenticationToken authentication = new JwtAuthenticationToken(
                        userPrincipal,
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            // 예외 처리 로직이 필요하면 추가
        }
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}