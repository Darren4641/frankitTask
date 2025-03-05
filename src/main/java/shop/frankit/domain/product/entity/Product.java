package shop.frankit.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import shop.frankit.domain.common.BaseEntity;
import shop.frankit.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product", indexes = @Index(name = "idx_product_user_id", columnList = "user_id"))
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;                // 상품 이름
    private String description;         // 상품 설명
    private Double price;               // 가격
    private Double deliveryFee;         // 배송비

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductOption> options = new ArrayList<>();

    public Product(Long id, String name, String description, Double price, Double deliveryFee) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.deliveryFee = deliveryFee;
    }

    public Product(String name, String description, Double price, Double deliveryFee) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.deliveryFee = deliveryFee;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void addOption(ProductOption option) {
        option.setProduct(this);
        this.options.add(option);
    }
}