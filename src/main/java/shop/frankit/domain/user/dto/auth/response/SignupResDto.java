package shop.frankit.domain.user.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.common.security.dto.RoleType;
import shop.frankit.domain.user.dto.auth.service.SignupSvcResDto;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupResDto {
    private String email;
    private Set<RoleType> roles;

    public static SignupResDto from(SignupSvcResDto svcResDto) {
        return new SignupResDto(svcResDto.getEmail(), svcResDto.getRoles());
    }
}
