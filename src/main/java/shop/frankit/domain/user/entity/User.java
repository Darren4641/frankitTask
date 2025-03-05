package shop.frankit.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import shop.frankit.domain.common.BaseEntity;
import shop.frankit.domain.product.entity.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "`user`")
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, unique = true, length = 255)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    // ✅ `@OneToMany`로 변경
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<UserRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public User(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public User(String email, Set<UserRole> roles) {
        this.email = email;
        this.roles = roles;
    }

    public User(String email, String password, Set<UserRole> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRole(Set<UserRole> roles) {
        this.roles = roles;
    }

    public void addRole(UserRole role) {
        role.setUser(this);
        this.roles.add(role);
    }

    public void addProduct(Product product) {
        product.setUser(this);
        this.products.add(product);
    }

}
