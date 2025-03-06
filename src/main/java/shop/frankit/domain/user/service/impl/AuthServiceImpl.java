package shop.frankit.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import shop.frankit.common.exception.ApiErrorException;
import shop.frankit.common.response.ResultCode;
import shop.frankit.common.security.dto.UserPrincipal;
import shop.frankit.common.security.service.AuthTokenProvider;
import shop.frankit.domain.user.dto.auth.service.ProfileDto;
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
        userRepository.findByEmailDsl(signupSvcReqDto.getEmail())
                .ifPresent(savedUser -> {throw new ApiErrorException(ResultCode.ALREADY_SIGNUP); });

        User userEntity = userRepository.save(signupSvcReqDto.toEntity());

        return SignupSvcResDto.fromEntity(userEntity);
    }

    @Override
    public SigninSvcResDto validateCredentials(SigninSvcReqDto signinSvcReqDto) {
        var authentication = authenticateUser(signinSvcReqDto.getEmail(), signinSvcReqDto.getPassword());
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        User userEntity = userRepository.findByEmailDsl(principal.getUsername())
                .orElseThrow(() -> new ApiErrorException(ResultCode.USER_NOT_FOUND));

        ProfileDto profileDto = ProfileDto.fromEntity(userEntity);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var token = tokenProvider.createToken(profileDto.getEmail(), profileDto.getRoles());

        return SigninSvcResDto.from(profileDto.getEmail(), profileDto.getRoles(), token);
    }

    @Override
    public ProfileDto getUserProfile() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        User userEntity = userRepository.findByEmailDsl(principal.getUsername())
                .orElseThrow(() -> new ApiErrorException(ResultCode.USER_NOT_FOUND));

        return ProfileDto.fromEntity(userEntity);
    }

    @Override
    public User authenticate() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        return userRepository.findByEmailDsl(principal.getUsername())
                .orElseThrow(() -> new ApiErrorException(ResultCode.USER_NOT_FOUND));
    }

    private Authentication authenticateUser(String email, String password) {
        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiErrorException(ResultCode.USER_NOT_FOUND);
        }
    }
}
