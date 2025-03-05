package shop.frankit.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import shop.frankit.domain.user.entity.User;

import java.util.Optional;

import static shop.frankit.domain.user.entity.QUser.user;
import static shop.frankit.domain.user.entity.QUserRole.userRole;

public class UserDslRepositoryImpl implements UserDslRepository {
    private final JPAQueryFactory queryFactory;

    public UserDslRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Optional<User> findByEmailDsl(String email) {
        return Optional.ofNullable(
                queryFactory
                .select(user)
                .from(user)
                .leftJoin(user.roles, userRole).fetchJoin()
                .where(user.email.eq(email))
                .fetchOne());
    }
}
