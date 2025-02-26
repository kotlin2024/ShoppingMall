package hjp.shoppingmall.domain.member.service;

import hjp.shoppingmall.domain.member.dto.LoginDto;
import hjp.shoppingmall.domain.member.dto.MemberRole;
import hjp.shoppingmall.domain.member.dto.SignUpDto;
import hjp.shoppingmall.domain.member.entity.MemberEntity;
import hjp.shoppingmall.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Transactional
    public String signUp(SignUpDto signUpDto){

        String loginId = signUpDto.getLoginId();
        String password = signUpDto.getPassword();
        String nickname = signUpDto.getNickname();
        boolean isMerchant = signUpDto.getIsMerchant();
        String memberRole = MemberRole.USER.toString();

        if(memberRepository.existsByLoginId(loginId)){
            throw new IllegalArgumentException("이미 존재하는 로그인 id 입니다.");
        }
        if(isMerchant) {
            memberRole = MemberRole.MERCHANT.toString();
        }

        memberRepository.save(new MemberEntity(loginId, password, nickname, memberRole));

        return "성공적으로 회원가입이 완료되었습니다!";
    }

    public String login(LoginDto loginDto) {
        return "";
    }
}
