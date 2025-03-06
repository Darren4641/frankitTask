package shop.frankit.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import shop.frankit.domain.product.dto.productoptionvalue.CommonOptionDto;

@Getter
@Entity
@Table(name = "`common_option`")
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String optionName;


    public CommonOption(String optionName) {
        this.optionName = optionName;
    }

    public CommonOptionDto toDto() {
        return new CommonOptionDto(
                id,
                optionName
        );
    }
}
