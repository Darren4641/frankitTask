package shop.frankit.domain.product.dto.product.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Double deliveryFee;
    private LocalDateTime createdDate;
    private String sellerEmail;
    private List<ProductOptionDetailDto> options;

    public ProductDetailDto(Long id, String name, String description, Double price,
                            Double deliveryFee, LocalDateTime createdDate, String sellerEmail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.deliveryFee = deliveryFee;
        this.createdDate = createdDate;
        this.sellerEmail = sellerEmail;
    }

    public void addOptions(List<ProductOptionDetailDto> options) {
        this.options = options;
    }
}
