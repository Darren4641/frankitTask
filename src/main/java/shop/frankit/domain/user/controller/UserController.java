package shop.frankit.domain.user.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.frankit.common.response.BaseResponse;
import shop.frankit.domain.user.service.AuthService;

@RestController
@RequestMapping("/user/v1")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;


    @GetMapping("/profile")
    @SecurityRequirement(name = "bearerAuth")
    public BaseResponse<?> profile() {
        return new BaseResponse<>(authService.authenticate());
    }

}
