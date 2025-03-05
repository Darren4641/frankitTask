package shop.frankit.domain.user.dto.auth.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.common.security.dto.RoleType;
import shop.frankit.domain.user.entity.User;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupSvcResDto {
    private String email;
    private Set<RoleType> roles;

    public static SignupSvcResDto fromEntity(User user) {
        return new SignupSvcResDto(user.getEmail(), user.getRoles());
    }
}
