package shop.frankit.domain.product.dto.product.registration.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionValueRegistrationReqDto {
    @NotNull(message = "`value`은 필수 값 입니다.")
    private String value;
}
