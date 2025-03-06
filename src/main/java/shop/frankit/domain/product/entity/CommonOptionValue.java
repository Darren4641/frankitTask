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
@Table(name = "`common_option_value`")
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
