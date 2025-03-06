package shop.frankit.domain.product.dto.productoption.registration.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.OptionType;
import shop.frankit.domain.product.entity.CommonOption;
import shop.frankit.domain.product.entity.Product;
import shop.frankit.domain.product.entity.ProductOption;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionSelectRegistrationSvcReqDto {
    private Long productId;
    private Long optionId;
    private OptionType optionType;

    public ProductOption toEntity(Product product, CommonOption option) {
        ProductOption newProductOption = new ProductOption(
                optionType,
                option
        );
        product.addOption(newProductOption);
        return newProductOption;
    }
}
