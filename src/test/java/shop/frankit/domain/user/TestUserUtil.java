package shop.frankit.domain.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import shop.frankit.common.security.dto.RoleType;
import shop.frankit.domain.user.entity.User;
import shop.frankit.domain.user.entity.UserRole;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.mock;

public class TestUserUtil {

    public static User getAuthUser() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        User userMock = new User("test@example.com", "password", new HashSet<>());
        userMock.setRole(Set.of(new UserRole(userMock, RoleType.USER)));

        return userMock;
    }
}