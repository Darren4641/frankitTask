package shop.frankit.domain.product.dto.productoption.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.OptionType;
import shop.frankit.domain.product.entity.ProductOption;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionRegistrationSvcResDto {
    private Long productId;
    private String optionName;
    private OptionType optionType;
    private Double extraPrice;

    public static ProductOptionRegistrationSvcResDto fromEntity(Long productId, ProductOption productOption) {
        return new ProductOptionRegistrationSvcResDto(
                productId,
                productOption.getOptionName(),
                productOption.getOptionType(),
                productOption.getExtraPrice()
        );
    }
}
