package shop.frankit.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.frankit.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserDslRepository {

}
