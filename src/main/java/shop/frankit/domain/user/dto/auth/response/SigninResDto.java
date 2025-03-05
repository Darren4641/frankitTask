package shop.frankit.domain.user.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.common.security.dto.RoleType;
import shop.frankit.domain.user.dto.auth.service.SigninSvcResDto;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SigninResDto {
    private String email;
    private Set<RoleType> roles;
    private String token;

    public static SigninResDto from(SigninSvcResDto svcResDto) {
        return new SigninResDto(svcResDto.getEmail(), svcResDto.getRoles(), svcResDto.getToken());
    }
}
