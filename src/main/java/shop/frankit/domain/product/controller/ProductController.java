package shop.frankit.domain.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.frankit.common.response.BaseResponse;
import shop.frankit.domain.product.dto.registration.request.ProductRegistrationReqDto;
import shop.frankit.domain.product.dto.registration.response.ProductRegistrationResDto;
import shop.frankit.domain.product.dto.registration.service.ProductRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.registration.service.ProductRegistrationSvcResDto;
import shop.frankit.domain.product.service.ProductService;
import shop.frankit.domain.user.service.AuthService;

@RestController
@RequestMapping("/product/v1")
@RequiredArgsConstructor
public class ProductController {
    private final AuthService authService;
    private final ProductService productService;

    @PostMapping("/registration")
    public BaseResponse<ProductRegistrationResDto> registerProduct(@RequestBody @Valid ProductRegistrationReqDto requestDto) {
        ProductRegistrationSvcReqDto serviceRequest = requestDto.toServiceDto();

        ProductRegistrationSvcResDto serviceResponse = productService.saveProduct(serviceRequest);

        return new BaseResponse<>(ProductRegistrationResDto.from(serviceResponse));
    }


}
