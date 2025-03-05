package shop.frankit.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.frankit.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductDslRepository {
}
