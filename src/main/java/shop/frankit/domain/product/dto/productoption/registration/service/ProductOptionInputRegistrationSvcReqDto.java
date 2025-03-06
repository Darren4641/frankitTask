package shop.frankit.domain.product.dto.productoption.registration.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.OptionType;
import shop.frankit.domain.product.entity.Product;
import shop.frankit.domain.product.entity.ProductOption;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionInputRegistrationSvcReqDto {
    private Long productId;
    private String optionName;
    private OptionType optionType;
    private Double extraPrice;

    public ProductOption toEntity(Product product) {
        ProductOption newProductOption = new ProductOption(
                optionName,
                optionType,
                extraPrice
        );
        product.addOption(newProductOption);
        return newProductOption;
    }
}
