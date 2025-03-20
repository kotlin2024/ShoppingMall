package hjp.shoppingmall.domain.member.dto;


import lombok.*;

@NoArgsConstructor
@Data
public class SignUpDto {
    private String loginId;
    private String password;
    private String nickname;
    private Boolean isMerchant;
}
