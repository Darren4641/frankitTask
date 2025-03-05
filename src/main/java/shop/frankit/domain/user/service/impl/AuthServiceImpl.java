package shop.frankit.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import shop.frankit.common.exception.ApiErrorException;
import shop.frankit.common.response.ResultCode;
import shop.frankit.common.security.service.AuthTokenProvider;
import shop.frankit.domain.user.dto.auth.service.AuthenticateDto;
import shop.frankit.domain.user.dto.auth.service.SigninSvcReqDto;
import shop.frankit.domain.user.dto.auth.service.SigninSvcResDto;
import shop.frankit.domain.user.dto.auth.service.SignupSvcReqDto;
import shop.frankit.domain.user.dto.auth.service.SignupSvcResDto;
import shop.frankit.domain.user.entity.User;
import shop.frankit.domain.user.repository.UserRepository;
import shop.frankit.domain.user.service.AuthService;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final AuthTokenProvider tokenProvider;
    private final UserRepository userRepository;


    @Override
    public SignupSvcResDto saveUser(SignupSvcReqDto signupSvcReqDto) {
        userRepository.findByEmail(signupSvcReqDto.getEmail())
                .ifPresent(savedUser -> {throw new ApiErrorException(ResultCode.ALREADY_SIGNUP); });

        User userEntity = userRepository.save(signupSvcReqDto.toEntity());

        return SignupSvcResDto.fromEntity(userEntity);
    }

    @Override
    public SigninSvcResDto validateCredentials(SigninSvcReqDto signinSvcReqDto) {
        var authentication = authenticateUser(signinSvcReqDto.getEmail(), signinSvcReqDto.getPassword());

        AuthenticateDto authenticateDto = AuthenticateDto.fromAuthentication(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var token = tokenProvider.createToken(authenticateDto.getEmail(), authenticateDto.getRoles());

        return SigninSvcResDto.from(authenticateDto.getEmail(), authenticateDto.getRoles(), token);
    }

    @Override
    public AuthenticateDto authenticate() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return AuthenticateDto.fromAuthentication(authentication);
    }

    private Authentication authenticateUser(String email, String password) {
        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (Exception e) {
            throw new ApiErrorException(ResultCode.USER_NOT_FOUND);
        }
    }
}
