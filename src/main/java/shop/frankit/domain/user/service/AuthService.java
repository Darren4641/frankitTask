package shop.frankit.domain.user.service;

import shop.frankit.domain.user.dto.auth.service.ProfileDto;
import shop.frankit.domain.user.dto.auth.service.SigninSvcReqDto;
import shop.frankit.domain.user.dto.auth.service.SigninSvcResDto;
import shop.frankit.domain.user.dto.auth.service.SignupSvcReqDto;
import shop.frankit.domain.user.dto.auth.service.SignupSvcResDto;
import shop.frankit.domain.user.entity.User;

public interface AuthService {
    SignupSvcResDto saveUser(SignupSvcReqDto signupSvcReqDto);

    SigninSvcResDto validateCredentials(SigninSvcReqDto signinSvcReqDto);

    ProfileDto getUserProfile();

    User authenticate();
}
