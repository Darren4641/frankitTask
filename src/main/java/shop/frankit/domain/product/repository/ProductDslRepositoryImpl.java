package shop.frankit.domain.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class ProductDslRepositoryImpl implements ProductDslRepository {
    private final JPAQueryFactory queryFactory;

    public ProductDslRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


}
