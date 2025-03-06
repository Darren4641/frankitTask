package shop.frankit.domain.product.dto.product.modify.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.product.modify.service.ProductModifySvcResDto;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcResDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductModifyResDto {
    private Long productId;

    private String name;

    private String description;

    private Double price;

    private Double deliveryFee;

    public static ProductModifyResDto from(ProductModifySvcResDto svcResDto) {
        return new ProductModifyResDto(
                svcResDto.getProductId(),
                svcResDto.getName(),
                svcResDto.getDescription(),
                svcResDto.getPrice(),
                svcResDto.getDeliveryFee()
        );
    }
}
