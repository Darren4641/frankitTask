package shop.frankit.domain.product.dto.product.registration.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.entity.Product;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRegistrationSvcResDto {
    private Long productId;

    private String name;

    private String description;

    private Double price;

    private Double deliveryFee;

    public static ProductRegistrationSvcResDto fromEntity(Product product) {
        return new ProductRegistrationSvcResDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getDeliveryFee()
        );
    }
}
