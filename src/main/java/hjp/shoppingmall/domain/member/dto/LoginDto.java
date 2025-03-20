package hjp.shoppingmall.domain.member.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class LoginDto {
    private String loginId;
    private String password;
}
