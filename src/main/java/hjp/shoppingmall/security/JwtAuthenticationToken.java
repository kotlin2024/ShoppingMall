package hjp.shoppingmall.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserPrincipal userPrincipal;

    // 생성자
    public JwtAuthenticationToken(UserPrincipal userPrincipal, WebAuthenticationDetails details) {
        super(userPrincipal.getAuthorities());
        this.userPrincipal = userPrincipal;
        setAuthenticated(true);  // 인증된 상태로 설정
        setDetails(details);
    }

    @Override
    public Object getCredentials() {
        return null;  // 자격 증명 정보는 필요하지 않으므로 null 반환
    }

    @Override
    public Object getPrincipal() {
        return userPrincipal;  // 사용자 정보를 반환
    }

    @Override
    public boolean isAuthenticated() {
        return true;  // 항상 인증된 상태로 처리
    }
}
