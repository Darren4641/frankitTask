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
public class SignupSvcReqDto {
    private String email;
    private String encPassword;
    private Set<RoleType> roles;


    public User toEntity() {
        return new User(email, encPassword, roles);
    }
}
