package shop.frankit.domain.product.repository.product;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import shop.frankit.domain.product.entity.Product;

import java.util.Optional;

import static shop.frankit.domain.product.entity.QProduct.product;
import static shop.frankit.domain.product.entity.QProductOption.productOption;
import static shop.frankit.domain.user.entity.QUser.user;

public class ProductDslRepositoryImpl implements ProductDslRepository {
    private final JPAQueryFactory queryFactory;

    public ProductDslRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<Product> findByIdDsl(Long productId, Long userId) {
        return Optional.ofNullable(queryFactory
                .select(product)
                .from(product)
                .join(product.user, user).fetchJoin()
                .where(product.id.eq(productId)
                        .and(product.user.id.eq(userId)))
                .fetchOne());
    }

    public long countProductOptions(Long productId) {
        return Optional.ofNullable(queryFactory
                        .select(productOption.count()) // 옵션 개수 조회
                        .from(productOption)
                        .where(productOption.product.id.eq(productId))
                        .fetchOne())
                .orElse(0L);
    }

}
