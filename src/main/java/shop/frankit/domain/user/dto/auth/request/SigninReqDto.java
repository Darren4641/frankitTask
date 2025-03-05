package shop.frankit.domain.user.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.user.dto.auth.service.SigninSvcReqDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SigninReqDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public SigninSvcReqDto toServiceDto() {
        return new SigninSvcReqDto(
                email,
                password
        );
    }
}
