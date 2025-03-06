package shop.frankit.domain.product.dto.product.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Double deliveryFee;
    private LocalDateTime createdDate;
    private String sellerEmail;
}
