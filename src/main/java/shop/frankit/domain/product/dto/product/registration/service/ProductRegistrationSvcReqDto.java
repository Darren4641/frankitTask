package shop.frankit.domain.product.dto.product.registration.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.frankit.domain.product.entity.Product;
import shop.frankit.domain.user.dto.auth.service.ProfileDto;
import shop.frankit.domain.user.entity.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRegistrationSvcReqDto {
    private String name;

    private String description;

    private Double price;

    private Double deliveryFee;

    public Product toEntity(User authUser) {
        Product newProduct = new Product(
                name,
                description,
                price,
                deliveryFee
        );
        authUser.addProduct(newProduct);
        return newProduct;
    }
}
