package shop.frankit.domain.product.dto.product.registration.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcReqDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRegistrationReqDto {
    @NotNull(message = "`name`은 필수 값 입니다.")
    private String name;

    @NotNull(message = "`description`은 필수 값 입니다.")
    private String description;

    @NotNull(message = "`price`은 필수 값 입니다.")
    private Double price;

    @NotNull(message = "`deliveryFee`은 필수 값 입니다.")
    private Double deliveryFee;


    public ProductRegistrationSvcReqDto toServiceDto() {
        return new ProductRegistrationSvcReqDto(
                name,
                description,
                price,
                deliveryFee
        );
    }
}
