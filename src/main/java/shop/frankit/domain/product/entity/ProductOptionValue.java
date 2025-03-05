package shop.frankit.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import shop.frankit.domain.common.BaseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "`product_option_value`")
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOptionValue extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_option_id", nullable = false)
    private ProductOption productOption;

    private String value;

    protected void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

}