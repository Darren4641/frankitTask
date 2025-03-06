package shop.frankit.domain.product.dto.productoption.registration.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.OptionType;
import shop.frankit.domain.product.entity.ProductOption;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionSelectRegistrationSvcResDto {
    private Long productId;
    private String optionName;
    private OptionType optionType;
    private Double extraPrice;

    public static ProductOptionSelectRegistrationSvcResDto fromEntity(Long productId, ProductOption productOption) {
        return new ProductOptionSelectRegistrationSvcResDto(
                productId,
                productOption.getOptionName(),
                productOption.getOptionType(),
                productOption.getExtraPrice()
        );
    }
}
