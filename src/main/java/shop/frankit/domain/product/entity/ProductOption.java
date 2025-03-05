package shop.frankit.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import shop.frankit.domain.common.BaseEntity;
import shop.frankit.domain.product.dto.OptionType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "`product_option`")
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String optionName;  // 옵션 이름

    @Enumerated(EnumType.STRING)
    private OptionType optionType;  // 옵션 타입 (INPUT, SELECT)

    private Double extraPrice; // 옵션 추가 금액

    @OneToMany(mappedBy = "productOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOptionValue> optionValues = new ArrayList<>();

    public ProductOption(String optionName, OptionType optionType, Double extraPrice) {
        this.optionName = optionName;
        this.optionType = optionType;
        this.extraPrice = extraPrice;
    }

    protected void setProduct(Product product) {
        this.product = product;
    }

    public void addOptionValue(ProductOptionValue value) {
        value.setProductOption(this);
        this.optionValues.add(value);
    }
}