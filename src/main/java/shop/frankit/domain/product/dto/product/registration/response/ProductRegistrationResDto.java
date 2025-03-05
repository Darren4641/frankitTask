package shop.frankit.domain.product.dto.product.registration.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcResDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRegistrationResDto {
    private Long productId;

    private String name;

    private String description;

    private Double price;

    private Double deliveryFee;

    public static ProductRegistrationResDto from(ProductRegistrationSvcResDto svcResDto) {
        return new ProductRegistrationResDto(
                svcResDto.getProductId(),
                svcResDto.getName(),
                svcResDto.getDescription(),
                svcResDto.getPrice(),
                svcResDto.getDeliveryFee()
        );
    }
}
