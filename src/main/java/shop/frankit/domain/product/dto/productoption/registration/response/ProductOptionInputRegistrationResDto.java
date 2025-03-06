package shop.frankit.domain.product.dto.productoption.registration.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.OptionType;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionInputRegistrationSvcResDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionInputRegistrationResDto {
    private Long productId;
    private String optionName;
    private OptionType optionType;
    private Double extraPrice;

    public static ProductOptionInputRegistrationResDto from(ProductOptionInputRegistrationSvcResDto svcResDto) {
        return new ProductOptionInputRegistrationResDto(
                svcResDto.getProductId(),
                svcResDto.getOptionName(),
                svcResDto.getOptionType(),
                svcResDto.getExtraPrice()
        );
    }
}
