package shop.frankit.domain.product.dto.productoption.registration.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.OptionType;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionSelectRegistrationSvcResDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionSelectRegistrationResDto {
    private Long productId;
    private String optionName;
    private OptionType optionType;
    private Double extraPrice;

    public static ProductOptionSelectRegistrationResDto from(ProductOptionSelectRegistrationSvcResDto svcResDto) {
        return new ProductOptionSelectRegistrationResDto(
                svcResDto.getProductId(),
                svcResDto.getOptionName(),
                svcResDto.getOptionType(),
                svcResDto.getExtraPrice()
        );
    }

}
