package hjp.shoppingmall.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "loginId")
    private String loginId;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "ROLE")
    private String memberRole;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createTime;

    @PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now();
    }

    public MemberEntity(String loginId, String password, String nickname, String memberRole) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.memberRole = memberRole;
    }
}
