package shop.frankit.domain.product.dto.productoption.delete.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.OptionType;
import shop.frankit.domain.product.entity.ProductOption;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionDeleteSvcResDto {
    private String optionName;
    private OptionType optionType;
    private Double extraPrice;

    public static ProductOptionDeleteSvcResDto fromEntity(ProductOption productOption) {
        return new ProductOptionDeleteSvcResDto(
                productOption.getOptionName(),
                productOption.getOptionType(),
                productOption.getExtraPrice()
        );
    }
}
