package shop.frankit.domain.product.dto.productoption.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.OptionType;
import shop.frankit.domain.product.dto.productoption.service.ProductOptionRegistrationSvcResDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionRegistrationResDto {
    private Long productId;
    private String optionName;
    private OptionType optionType;
    private Double extraPrice;

    public static ProductOptionRegistrationResDto from(ProductOptionRegistrationSvcResDto svcResDto) {
        return new ProductOptionRegistrationResDto(
                svcResDto.getProductId(),
                svcResDto.getOptionName(),
                svcResDto.getOptionType(),
                svcResDto.getExtraPrice()
        );
    }
}
