package shop.frankit.domain.product.dto.productoption.delete.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.OptionType;
import shop.frankit.domain.product.dto.productoption.delete.service.ProductOptionDeleteSvcReqDto;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionInputRegistrationSvcReqDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionDeleteReqDto {
    @NotNull(message = "`optionId`은 필수 값 입니다.")
    private Long optionId;

    public ProductOptionDeleteSvcReqDto toServiceDto() {
        return new ProductOptionDeleteSvcReqDto(
                optionId
        );
    }
}
