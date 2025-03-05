package shop.frankit.domain.user.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import shop.frankit.common.exception.ApiErrorException;
import shop.frankit.common.response.ResultCode;
import shop.frankit.common.security.dto.RoleType;
import shop.frankit.common.security.dto.UserPrincipal;
import shop.frankit.common.security.service.AuthTokenProvider;
import shop.frankit.domain.user.dto.auth.service.*;
import shop.frankit.domain.user.entity.User;
import shop.frankit.domain.user.entity.UserRole;
import shop.frankit.domain.user.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private AuthTokenProvider tokenProvider;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    @DisplayName("회원가입 - 성공적으로 저장")
    void saveUser_Success() {
        // given
        SignupSvcReqDto signupRequest = new SignupSvcReqDto("test@example.com", "password", Set.of(RoleType.USER));
        User userEntity = signupRequest.toEntity();

        given(userRepository.findByEmailDsl(signupRequest.getEmail())).willReturn(Optional.empty());
        given(userRepository.save(any(User.class))).willReturn(userEntity);

        // when
        SignupSvcResDto response = authService.saveUser(signupRequest);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo(signupRequest.getEmail());

        verify(userRepository).findByEmailDsl(signupRequest.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("회원가입 - 이미 가입된 이메일이면 예외 발생")
    void saveUser_ThrowsException_WhenEmailAlreadyExists() {
        // given
        SignupSvcReqDto signupRequest = new SignupSvcReqDto("test@example.com", "password", Set.of(RoleType.USER));
        given(userRepository.findByEmailDsl(signupRequest.getEmail())).willReturn(Optional.ofNullable(signupRequest.toEntity()));

        // when & then
        assertThatThrownBy(() -> authService.saveUser(signupRequest))
                .isInstanceOf(ApiErrorException.class)
                .hasMessageContaining(ResultCode.ALREADY_SIGNUP.getMessage());

        verify(userRepository).findByEmailDsl(signupRequest.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("로그인 - 유효한 자격증명")
    void validateCredentials_Success() {
        // given
        SigninSvcReqDto signinRequest = new SigninSvcReqDto("test@example.com", "password");
        Authentication authentication = mock(Authentication.class);
        User userMock = new User("test@example.com", "password", new HashSet<>());
        userMock.addRole(Set.of(new UserRole(userMock, RoleType.USER)));

        UserPrincipal mockUserPrincipal = new UserPrincipal(userMock);


        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willReturn(authentication);

        given(authentication.getPrincipal()).willReturn((UserPrincipal) mockUserPrincipal);
        given(tokenProvider.createToken(eq(signinRequest.getEmail()), eq(Set.of(RoleType.USER))))
                .willReturn("mocked-jwt-token");

        // when
        SigninSvcResDto response = authService.validateCredentials(signinRequest);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo(signinRequest.getEmail());
        assertThat(response.getToken()).isEqualTo("mocked-jwt-token");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenProvider).createToken(eq(signinRequest.getEmail()), eq(Set.of(RoleType.USER)));
    }

    @Test
    @DisplayName("로그인 - 잘못된 자격증명 시 예외 발생")
    void validateCredentials_ThrowsException_WhenInvalidCredentials() {
        // given
        SigninSvcReqDto signinRequest = new SigninSvcReqDto("test@example.com", "wrongpassword");

        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willThrow(new BadCredentialsException("Invalid credentials"));

        // when & then
        assertThatThrownBy(() -> authService.validateCredentials(signinRequest))
                .isInstanceOf(ApiErrorException.class)
                .hasMessageContaining(ResultCode.USER_NOT_FOUND.getMessage());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenProvider, never()).createToken(anyString(), anyList());
    }

    @Test
    @DisplayName("현재 인증된 사용자 가져오기")
    void authenticate_ReturnsAuthenticatedUser() {
        // given
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        User userMock = new User("test@example.com", "password", new HashSet<>());
        userMock.addRole(Set.of(new UserRole(userMock, RoleType.USER)));

        UserPrincipal mockUserPrincipal = new UserPrincipal(userMock);

        given(securityContext.getAuthentication()).willReturn(authentication);
        given(authentication.getPrincipal()).willReturn((UserPrincipal) mockUserPrincipal);
        SecurityContextHolder.setContext(securityContext);

        // when
        AuthenticateDto response = authService.authenticate();

        // then
        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo("test@example.com");

        verify(securityContext).getAuthentication();
    }


}