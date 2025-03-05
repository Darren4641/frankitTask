package shop.frankit.domain.product.dto.registration.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionRegistrationReqDto {
    @NotNull(message = "`optionName`은 필수 값 입니다.")
    private String optionName;
    @NotNull(message = "`extraPrice`은 필수 값 입니다.")
    private Double extraPrice;
    @NotNull(message = "`productId`은 필수 값 입니다.")
    private Long productId;
}
