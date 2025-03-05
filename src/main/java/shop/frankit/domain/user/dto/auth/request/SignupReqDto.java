package shop.frankit.domain.user.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.common.security.dto.RoleType;
import shop.frankit.domain.user.dto.auth.service.SignupSvcReqDto;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupReqDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public SignupSvcReqDto toServiceDto(String encPassword, Set<RoleType> roles) {
        return new SignupSvcReqDto(
                email,
                encPassword,
                roles
        );
    }
}