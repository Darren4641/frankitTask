package shop.frankit.domain.user.dto.auth.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import shop.frankit.common.security.dto.RoleType;
import shop.frankit.common.security.dto.UserPrincipal;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateDto {
    private String email;
    private Set<RoleType> roles;

    public static AuthenticateDto fromAuthentication(Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        var roles = principal.getAuthorities().stream()
                .map(authority -> RoleType.valueOf(authority.getAuthority()))
                .collect(Collectors.toSet());

        return new AuthenticateDto(principal.getUsername(), roles);
    }
}
