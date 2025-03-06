package shop.frankit.domain.product.dto.product.delete.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.product.delete.service.ProductDeleteSvcReqDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDeleteReqDto {
    private Long productId;

    public ProductDeleteSvcReqDto toServiceDto() {
        return new ProductDeleteSvcReqDto(productId);
    }
}
