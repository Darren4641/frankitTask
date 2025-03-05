package shop.frankit.domain.product.dto.registration.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRegistrationSvcResDto {
    private Long productId;

    private String name;

    private String description;

    private Double price;

    private Double deliveryFee;

}
