package shop.frankit.domain.user.dto.auth.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.common.security.dto.RoleType;
import shop.frankit.domain.user.entity.User;
import shop.frankit.domain.user.entity.UserRole;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupSvcReqDto {
    private String email;
    private String encPassword;
    private Set<RoleType> roles;


    public User toEntity() {
        User newUser = new User(email, encPassword, new HashSet<>());

        Set<UserRole> userRoles = roles.stream()
                .map(role -> new UserRole(newUser, role))
                .collect(Collectors.toSet());

        newUser.addRole(userRoles);
        return newUser;
    }
}
