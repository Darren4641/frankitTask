package shop.frankit.domain.product.dto.productoption.registration.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.OptionType;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionInputRegistrationSvcReqDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionInputRegistrationReqDto {
    @NotNull(message = "`productId`은 필수 값 입니다.")
    private Long productId;
    @NotNull(message = "`optionName`은 필수 값 입니다.")
    private String optionName;
    @NotNull(message = "`extraPrice`은 필수 값 입니다.")
    private Double extraPrice;

    public ProductOptionInputRegistrationSvcReqDto toServiceDto() {
        return new ProductOptionInputRegistrationSvcReqDto(
                productId,
                optionName,
                OptionType.INPUT,
                extraPrice
        );
    }
}
