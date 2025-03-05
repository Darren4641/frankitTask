package shop.frankit.domain.user.repository;

import shop.frankit.domain.user.entity.User;

import java.util.Optional;

public interface UserDslRepository {
    Optional<User> findByEmailDsl(String email);
}
