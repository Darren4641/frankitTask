package shop.frankit.domain.product.dto.product.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.dto.OptionType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionDetailDto {
    private Long id;
    private OptionType optionType;
    private String optionName;
    private Double extraPrice;
}
