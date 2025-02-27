package hjp.shoppingmall.domain.member.service;

import hjp.shoppingmall.domain.member.dto.LoginDto;
import hjp.shoppingmall.domain.member.dto.MemberRole;
import hjp.shoppingmall.domain.member.dto.SignUpDto;
import hjp.shoppingmall.domain.member.entity.MemberEntity;
import hjp.shoppingmall.domain.member.repository.MemberRepository;
import hjp.shoppingmall.security.JwtTokenManager;
import hjp.shoppingmall.security.UserPrincipal;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenManager jwtTokenManager;

    public MemberService(
            MemberRepository memberRepository,
            JwtTokenManager jwtTokenManager
    ){
        this.memberRepository = memberRepository;
        this.jwtTokenManager = jwtTokenManager;
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

        String secretPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        memberRepository.save(new MemberEntity(loginId, secretPassword, nickname, memberRole));

        return "성공적으로 회원가입이 완료되었습니다!";
    }

    public String login(LoginDto loginDto) {

        String loginId = loginDto.getLoginId();
        String password = loginDto.getPassword();

        MemberEntity loginUser = memberRepository.findByLoginId(loginId);
        if(loginUser == null){
            System.out.println("로그인 ID가 존재하지 않음: " + loginId);
            throw new IllegalArgumentException("존재하지 않는 로그인 id 입니다.");
        }
        if(!BCrypt.checkpw(password, loginUser.getPassword())){
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }
        String token = jwtTokenManager.generateTokenResponse(loginUser.getId(), loginUser.getMemberRole());
        System.out.println("토큰 값: "+token);
        return token;
    }

    public String imsiCheck(UserPrincipal userPrincipal) {
        return "user id = "+ userPrincipal.getMemberId() + "user Role = " + userPrincipal.getMemberRole() ;
    }
}