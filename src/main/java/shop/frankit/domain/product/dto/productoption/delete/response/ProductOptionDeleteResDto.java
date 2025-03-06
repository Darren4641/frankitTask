package shop.frankit.domain.product.dto.productoption.delete.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.OptionType;
import shop.frankit.domain.product.dto.productoption.delete.service.ProductOptionDeleteSvcResDto;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionInputRegistrationSvcResDto;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionDeleteResDto {
    private String optionName;
    private OptionType optionType;
    private Double extraPrice;

    public static ProductOptionDeleteResDto from(ProductOptionDeleteSvcResDto svcResDto) {
        return new ProductOptionDeleteResDto(
                svcResDto.getOptionName(),
                svcResDto.getOptionType(),
                svcResDto.getExtraPrice()
        );
    }
}
