package hjp.shoppingmall.domain.member.controller;

import hjp.shoppingmall.domain.member.dto.LoginDto;
import hjp.shoppingmall.domain.member.dto.SignUpDto;
import hjp.shoppingmall.domain.member.service.MemberService;
import hjp.shoppingmall.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController{

    private final MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("/sign-up")
    private ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto){
        return ResponseEntity.ok().body(memberService.signUp(signUpDto));
    }

    @PostMapping("/login")
    private ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        return ResponseEntity.ok().body(memberService.login(loginDto));
    }

    @PostMapping("/check")
    private ResponseEntity<String> imsiCheck(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return ResponseEntity.ok().body(memberService.imsiCheck(userPrincipal));
    }
}
