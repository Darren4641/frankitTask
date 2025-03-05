package shop.frankit.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import shop.frankit.common.security.dto.RoleType;

import java.util.Set;

@Getter
@Entity
@Table(name = "`user`")
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, unique = true, length = 255)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.LAZY) // 다중 역할(Role) 지원
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role", nullable = false)
    private Set<RoleType> roles;

    public User(String email, Set<RoleType> roles) {
        this.email = email;
        this.roles = roles;
    }

    public User(String email, String password, Set<RoleType> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
