package shop.frankit.domain.product.dto.productoption.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.OptionType;
import shop.frankit.domain.product.dto.productoption.service.ProductOptionRegistrationSvcReqDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionRegistrationReqDto {
    @NotNull(message = "`productId`은 필수 값 입니다.")
    private Long productId;
    @NotNull(message = "`optionName`은 필수 값 입니다.")
    private String optionName;
    @NotNull(message = "`optionType`은 필수 값 입니다.")
    private OptionType optionType;
    @NotNull(message = "`extraPrice`은 필수 값 입니다.")
    private Double extraPrice;

    public ProductOptionRegistrationSvcReqDto toServiceDto() {
        return new ProductOptionRegistrationSvcReqDto(
                productId,
                optionName,
                optionType,
                extraPrice
        );
    }
}
