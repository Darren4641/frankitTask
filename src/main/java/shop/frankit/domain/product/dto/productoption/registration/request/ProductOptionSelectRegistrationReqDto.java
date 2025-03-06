package shop.frankit.domain.product.dto.productoption.registration.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.OptionType;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionInputRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionSelectRegistrationSvcReqDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionSelectRegistrationReqDto {
    @NotNull(message = "`productId`은 필수 값 입니다.")
    private Long productId;
    @NotNull(message = "`optionId`은 필수 값 입니다.")
    private Long optionId;

    public ProductOptionSelectRegistrationSvcReqDto toServiceDto() {
        return new ProductOptionSelectRegistrationSvcReqDto(
                productId,
                optionId,
                OptionType.SELECT
        );
    }

}
