package hjp.shoppingmall.domain.member.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignUpDto {
    private String loginId;
    private String password;
    private String nickname;
    private Boolean isMerchant;
}
