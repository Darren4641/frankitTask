package shop.frankit.domain.user.dto.auth.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.common.security.dto.RoleType;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SigninSvcResDto {
    private String email;
    private Set<RoleType> roles;
    private String token;

    public static SigninSvcResDto from(String email, Set<RoleType> roles, String token) {
        return new SigninSvcResDto(email, roles, token);
    }
}
