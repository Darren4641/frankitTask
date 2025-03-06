package shop.frankit.domain.product.dto.product.delete.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.product.delete.request.ProductDeleteReqDto;
import shop.frankit.domain.product.dto.product.delete.service.ProductDeleteSvcResDto;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcResDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDeleteResDto {
    private Long productId;

    private String name;

    private String description;

    private Double price;

    private Double deliveryFee;

    public static ProductDeleteResDto from(ProductDeleteSvcResDto svcResDto) {
        return new ProductDeleteResDto(
                svcResDto.getProductId(),
                svcResDto.getName(),
                svcResDto.getDescription(),
                svcResDto.getPrice(),
                svcResDto.getDeliveryFee()
        );
    }
}
