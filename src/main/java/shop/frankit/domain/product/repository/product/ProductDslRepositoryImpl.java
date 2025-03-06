package shop.frankit.domain.product.repository.product;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import shop.frankit.domain.product.dto.product.detail.ProductDetailDto;
import shop.frankit.domain.product.dto.product.detail.ProductOptionDetailDto;
import shop.frankit.domain.product.dto.product.info.ProductDto;
import shop.frankit.domain.product.entity.Product;

import java.util.List;
import java.util.Optional;

import static shop.frankit.domain.product.entity.QCommonOption.commonOption;
import static shop.frankit.domain.product.entity.QCommonOptionValue.commonOptionValue;
import static shop.frankit.domain.product.entity.QProduct.product;
import static shop.frankit.domain.product.entity.QProductOption.productOption;
import static shop.frankit.domain.user.entity.QUser.user;

public class ProductDslRepositoryImpl implements ProductDslRepository {
    private final JPAQueryFactory queryFactory;

    public ProductDslRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<Product> findByIdDsl(Long productId) {
        return Optional.ofNullable(queryFactory
                .select(product)
                .from(product)
                .join(product.user, user).fetchJoin()
                .where(product.id.eq(productId))
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

    public Page<ProductDto> findProductsPageDsl(Pageable pageable) {
        List<ProductDto> results =  queryFactory.select(
                Projections.constructor(
                        ProductDto.class,
                        product.id,
                        product.name,
                        product.description,
                        product.price,
                        product.deliveryFee,
                        product.createdDate,
                        user.email)
                )
                .from(product)
                .leftJoin(product.user, user)
                .offset(pageable.getOffset())  // 페이징 적용
                .limit(pageable.getPageSize())
                .orderBy(product.createdDate.desc())
                .fetch();

        Long total = queryFactory
                .select(product.id.count())
                .from(product)
                .fetchOne();

        return new PageImpl<>(results, pageable, total != null ? total : 0L);
    }

    public Optional<ProductDetailDto> findProductDsl(Long productId) {
        return Optional.ofNullable(queryFactory
                .select(
                        Projections.constructor(
                                ProductDetailDto.class,
                                product.id,
                                product.name,
                                product.description,
                                product.price,
                                product.deliveryFee,
                                product.createdDate,
                                user.email)
                )
                .from(product)
                .leftJoin(product.user, user)
                .where(product.id.eq(productId))
                .fetchOne());
    }

    public List<ProductOptionDetailDto> getOptions(Long productId) {
        return queryFactory
                .select(Projections.constructor(
                                ProductOptionDetailDto.class,
                                productOption.id,
                                productOption.optionType,
                                new CaseBuilder()
                                        .when(productOption.optionName.isNotNull())
                                        .then(productOption.optionName)
                                        .otherwise(
                                                Expressions.stringTemplate(
                                                        "concat({0}, ' (', {1}, ')')",
                                                        commonOption.optionName, commonOptionValue.name
                                                )
                                        ),
                                new CaseBuilder()
                                        .when(productOption.extraPrice.isNotNull())
                                        .then(productOption.extraPrice)
                                        .otherwise(commonOptionValue.price)
                        )
                )
                .from(productOption)
                .leftJoin(commonOption).on(commonOption.id.eq(productOption.commonOption.id))
                .leftJoin(commonOptionValue).on(commonOptionValue.commonOption.id.eq(commonOption.id))
                .where(productOption.product.id.eq(productId))
                .fetch();
    }

}
