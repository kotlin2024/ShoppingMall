package hjp.shoppingmall.domain.member.repository;

import hjp.shoppingmall.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    public boolean existsByLoginId(String loginId);
}
