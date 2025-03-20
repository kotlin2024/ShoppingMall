package hjp.shoppingmall.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserPrincipal {

    private Long memberId;
    private Collection<GrantedAuthority> authorities;
    private Set<String> memberRole;

    // 기본 생성자
    @JsonCreator
    public UserPrincipal(
            @JsonProperty("memberId") Long memberId,
            @JsonProperty("authorities") Collection<GrantedAuthority> authorities) {
        this.memberId = memberId;
        this.authorities = authorities != null ? authorities : java.util.Collections.emptyList();
        this.memberRole = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());  // 접두사 없이 그대로 role을 사용
    }

    // memberRole을 포함하는 생성자
    public UserPrincipal(Long memberId, Set<String> memberRole) {
        this.memberId = memberId;
        this.memberRole = memberRole != null ? memberRole : java.util.Collections.emptySet();
        this.authorities = memberRole.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role))
                .collect(Collectors.toList());
    }

    // getter 메소드들
    public Long getMemberId() {
        return memberId;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Set<String> getMemberRole() {
        return memberRole;
    }

    public String getRole() {
        return authorities.isEmpty() ? "ROLE_UNKNOWN" : authorities.iterator().next().getAuthority();
    }
}
