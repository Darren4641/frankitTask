package shop.frankit.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import shop.frankit.common.security.dto.RoleType;

@Getter
@Entity
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_role", indexes = @Index(name = "idx_user_role_user_id", columnList = "user_id"))
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    public UserRole(User user, RoleType role) {
        this.user = user;
        this.role = role;
        user.getRoles().add(this);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
