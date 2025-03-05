package shop.frankit.common.security.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Arrays;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RoleType {
    USER("ROLE_USER", "일반"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String role;
    private final String roleName;

    RoleType(String role, String roleName) {
        this.role = role;
        this.roleName = roleName;
    }

    public static RoleType fromRole(String role) {
        return Arrays.stream(values())
                .filter(r -> r.role.equals(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid role: " + role));
    }
}