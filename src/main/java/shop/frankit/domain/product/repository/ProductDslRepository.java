package shop.frankit.domain.product.repository;

import shop.frankit.domain.product.entity.Product;

import java.util.Optional;

public interface ProductDslRepository {
    Optional<Product> findByIdDsl(Long productId, Long userId);
    long countProductOptions(Long productId);
}
