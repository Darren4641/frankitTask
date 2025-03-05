package shop.frankit.domain.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.frankit.common.response.BaseResponse;
import shop.frankit.domain.product.dto.product.registration.request.ProductRegistrationReqDto;
import shop.frankit.domain.product.dto.product.registration.response.ProductRegistrationResDto;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcResDto;
import shop.frankit.domain.product.dto.productoption.request.ProductOptionRegistrationReqDto;
import shop.frankit.domain.product.dto.productoption.response.ProductOptionRegistrationResDto;
import shop.frankit.domain.product.dto.productoption.service.ProductOptionRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.productoption.service.ProductOptionRegistrationSvcResDto;
import shop.frankit.domain.product.service.ProductOptionService;
import shop.frankit.domain.product.service.ProductService;
import shop.frankit.domain.user.entity.User;
import shop.frankit.domain.user.service.AuthService;

@RestController
@RequestMapping("/product/v1")
@RequiredArgsConstructor
public class ProductController {
    private final AuthService authService;
    private final ProductService productService;
    private final ProductOptionService productOptionService;

    @PostMapping("/registration")
    public BaseResponse<ProductRegistrationResDto> registerProduct(@RequestBody @Valid ProductRegistrationReqDto requestDto) {
        ProductRegistrationSvcReqDto serviceRequest = requestDto.toServiceDto();

        User authUser = authService.authenticate();
        ProductRegistrationSvcResDto serviceResponse = productService.saveProduct(authUser, serviceRequest);

        return new BaseResponse<>(ProductRegistrationResDto.from(serviceResponse));
    }

    @PostMapping("/registration/option")
    public BaseResponse<ProductOptionRegistrationResDto> registerOption(@RequestBody @Valid ProductOptionRegistrationReqDto requestDto) {
        ProductOptionRegistrationSvcReqDto serviceRequest = requestDto.toServiceDto();

        User authUser = authService.authenticate();
        ProductOptionRegistrationSvcResDto serviceResponse = productOptionService.addOptionToProduct(authUser, serviceRequest);

        return new BaseResponse<>(ProductOptionRegistrationResDto.from(serviceResponse));
    }


}
