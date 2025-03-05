package shop.frankit.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import shop.frankit.common.response.BaseResponse;
import shop.frankit.common.security.dto.RoleType;
import shop.frankit.domain.user.dto.auth.request.SigninReqDto;
import shop.frankit.domain.user.dto.auth.request.SignupReqDto;
import shop.frankit.domain.user.dto.auth.response.SigninResDto;
import shop.frankit.domain.user.dto.auth.response.SignupResDto;
import shop.frankit.domain.user.dto.auth.service.SigninSvcReqDto;
import shop.frankit.domain.user.dto.auth.service.SigninSvcResDto;
import shop.frankit.domain.user.dto.auth.service.SignupSvcReqDto;
import shop.frankit.domain.user.dto.auth.service.SignupSvcResDto;
import shop.frankit.domain.user.service.AuthService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth/v1")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public BaseResponse<SignupResDto> signup(@RequestBody @Valid SignupReqDto requestDto) {
        Set<RoleType> roles = new HashSet<>(Set.of(RoleType.USER));

        SignupSvcReqDto serviceRequest = requestDto.toServiceDto(passwordEncoder.encode(requestDto.getPassword()), roles);

        SignupSvcResDto serviceResponse = authService.saveUser(serviceRequest);

        return new BaseResponse<>(SignupResDto.from(serviceResponse));
    }

    @PostMapping("/signin")
    public BaseResponse<SigninResDto> signin(@RequestBody @Valid SigninReqDto requestDto) {
        SigninSvcReqDto serviceRequest = requestDto.toServiceDto();

        SigninSvcResDto serviceResponse = authService.validateCredentials(serviceRequest);

        return new BaseResponse<>(SigninResDto.from(serviceResponse));
    }

}
