package hjp.shoppingmall.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@NoArgsConstructor
@Data
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "loginId")
    private String loginId;

    @Column(name = "password")
    @ToString.Exclude
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
    } // TODO() 해당 부분은 @AllArgsConstructor 어노테이션과  겹치는 부분이 있으니 확인해야하며 @data 를 사용하지 않는것이 좋아보임 추후에 수정할것 !!
}
