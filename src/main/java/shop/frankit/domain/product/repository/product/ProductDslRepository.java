package shop.frankit.domain.product.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.frankit.domain.product.dto.product.detail.ProductDetailDto;
import shop.frankit.domain.product.dto.product.detail.ProductOptionDetailDto;
import shop.frankit.domain.product.dto.product.info.ProductDto;
import shop.frankit.domain.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDslRepository {
    Optional<Product> findByIdDsl(Long productId);
    long countProductOptions(Long productId);

    Page<ProductDto> findProductsPageDsl(Pageable pageable);

    Optional<ProductDetailDto> findProductDsl(Long productId);

    List<ProductOptionDetailDto> getOptions(Long productId);
}
