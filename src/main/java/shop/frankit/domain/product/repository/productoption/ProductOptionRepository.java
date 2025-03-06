package shop.frankit.domain.product.repository.productoption;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.frankit.domain.product.entity.ProductOption;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
}
