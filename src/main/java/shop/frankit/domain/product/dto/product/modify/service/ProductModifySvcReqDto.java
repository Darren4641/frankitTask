package shop.frankit.domain.product.dto.product.modify.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.entity.Product;
import shop.frankit.domain.user.entity.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductModifySvcReqDto {
    private Long productId;

    private String name;

    private String description;

    private Double price;

    private Double deliveryFee;
}
