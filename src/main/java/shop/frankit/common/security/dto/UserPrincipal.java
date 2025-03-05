package shop.frankit.common.security.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shop.frankit.domain.user.entity.User;
import shop.frankit.domain.user.entity.UserRole;

import java.util.*;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {
    private final String email;
    private final String password;
    private final Set<RoleType> roles;

    public UserPrincipal(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles().stream()
                .map(UserRole::getRole)
                .collect(Collectors.toSet());
    }

    public UserPrincipal(User user, Map<String, Object> attributes) {
        this(user); // 기본 생성자 호출
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name())) // RoleType을 GrantedAuthority로 변환
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}