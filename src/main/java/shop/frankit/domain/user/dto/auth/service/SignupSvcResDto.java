package shop.frankit.domain.user.dto.auth.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.common.security.dto.RoleType;
import shop.frankit.domain.user.entity.User;
import shop.frankit.domain.user.entity.UserRole;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupSvcResDto {
    private String email;
    private Set<RoleType> roles;

    public static SignupSvcResDto fromEntity(User user) {
        return new SignupSvcResDto(user.getEmail(), user.getRoles().stream()
                .map(UserRole::getRole)
                .collect(Collectors.toSet()));
    }
}
