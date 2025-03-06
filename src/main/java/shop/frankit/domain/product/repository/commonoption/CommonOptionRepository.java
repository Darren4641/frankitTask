package shop.frankit.domain.product.repository.commonoption;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.frankit.domain.product.entity.CommonOption;

import java.util.Optional;

public interface CommonOptionRepository extends JpaRepository<CommonOption, Long> {
    Optional<CommonOption> findByOptionName(String optionName);
}
