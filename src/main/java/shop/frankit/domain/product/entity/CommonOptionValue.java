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
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`common_option_value`", indexes = @Index(name = "idx_common_option_value_common_option", columnList = "common_option_id"))
public class CommonOptionValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "common_option_id")
    private CommonOption commonOption;

    public CommonOptionValue(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public void setCommonOption(CommonOption commonOption) {
        this.commonOption = commonOption;
    }
}
