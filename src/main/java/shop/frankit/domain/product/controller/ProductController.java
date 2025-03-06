package shop.frankit.domain.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.frankit.common.response.BaseResponse;
import shop.frankit.domain.product.dto.product.registration.request.ProductRegistrationReqDto;
import shop.frankit.domain.product.dto.product.registration.response.ProductRegistrationResDto;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcResDto;
import shop.frankit.domain.product.dto.productoption.registration.request.ProductOptionInputRegistrationReqDto;
import shop.frankit.domain.product.dto.productoption.registration.request.ProductOptionSelectRegistrationReqDto;
import shop.frankit.domain.product.dto.productoption.registration.response.ProductOptionInputRegistrationResDto;
import shop.frankit.domain.product.dto.productoption.registration.response.ProductOptionSelectRegistrationResDto;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionInputRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionInputRegistrationSvcResDto;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionSelectRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionSelectRegistrationSvcResDto;
import shop.frankit.domain.product.dto.productoptionvalue.CommonOptionDto;
import shop.frankit.domain.product.service.CommonOptionService;
import shop.frankit.domain.product.service.ProductOptionService;
import shop.frankit.domain.product.service.ProductService;
import shop.frankit.domain.user.entity.User;
import shop.frankit.domain.user.service.AuthService;

import java.util.List;

@RestController
@RequestMapping("/product/v1")
@RequiredArgsConstructor
public class ProductController {
    private final AuthService authService;
    private final ProductService productService;
    private final ProductOptionService productOptionService;
    private final CommonOptionService commonOptionService;

    @PostMapping("/registration")
    public BaseResponse<ProductRegistrationResDto> registerProduct(@RequestBody @Valid ProductRegistrationReqDto requestDto) {
        ProductRegistrationSvcReqDto serviceRequest = requestDto.toServiceDto();

        User authUser = authService.authenticate();
        ProductRegistrationSvcResDto serviceResponse = productService.saveProduct(authUser, serviceRequest);

        return new BaseResponse<>(ProductRegistrationResDto.from(serviceResponse));
    }

    @PostMapping("/registration/input-option")
    public BaseResponse<ProductOptionInputRegistrationResDto> registerInputOption(@RequestBody @Valid ProductOptionInputRegistrationReqDto requestDto) {
        ProductOptionInputRegistrationSvcReqDto serviceRequest = requestDto.toServiceDto();

        User authUser = authService.authenticate();
        ProductOptionInputRegistrationSvcResDto serviceResponse = productOptionService.addInputOptionToProduct(authUser, serviceRequest);

        return new BaseResponse<>(ProductOptionInputRegistrationResDto.from(serviceResponse));
    }

    @PostMapping("/registration/select-option")
    public BaseResponse<ProductOptionSelectRegistrationResDto> registerSelectOption(@RequestBody @Valid ProductOptionSelectRegistrationReqDto requestDto) {
        ProductOptionSelectRegistrationSvcReqDto serviceRequest = requestDto.toServiceDto();

        User authUser = authService.authenticate();
        ProductOptionSelectRegistrationSvcResDto serviceResponse = productOptionService.addSelectOptionToProduct(authUser, serviceRequest);

        return new BaseResponse<>(ProductOptionSelectRegistrationResDto.from(serviceResponse));
    }


    @GetMapping("/common/option")
    public BaseResponse<List<CommonOptionDto>> viewCommonOption() {
        List<CommonOptionDto> serviceResponse = commonOptionService.getCommonOption();

        return new BaseResponse<>(serviceResponse);
    }


}
