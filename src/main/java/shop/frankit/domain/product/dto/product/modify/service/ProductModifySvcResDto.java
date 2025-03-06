package shop.frankit.domain.product.dto.product.modify.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.entity.Product;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductModifySvcResDto {
    private Long productId;

    private String name;

    private String description;

    private Double price;

    private Double deliveryFee;

    public static ProductModifySvcResDto fromEntity(Product product) {
        return new ProductModifySvcResDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getDeliveryFee()
        );
    }
}
