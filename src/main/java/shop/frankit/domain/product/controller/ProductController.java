package shop.frankit.domain.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import shop.frankit.common.response.BaseResponse;
import shop.frankit.domain.product.dto.product.delete.request.ProductDeleteReqDto;
import shop.frankit.domain.product.dto.product.delete.response.ProductDeleteResDto;
import shop.frankit.domain.product.dto.product.delete.service.ProductDeleteSvcReqDto;
import shop.frankit.domain.product.dto.product.delete.service.ProductDeleteSvcResDto;
import shop.frankit.domain.product.dto.product.detail.ProductDetailDto;
import shop.frankit.domain.product.dto.product.info.ProductDto;
import shop.frankit.domain.product.dto.product.modify.request.ProductModifyReqDto;
import shop.frankit.domain.product.dto.product.modify.response.ProductModifyResDto;
import shop.frankit.domain.product.dto.product.modify.service.ProductModifySvcReqDto;
import shop.frankit.domain.product.dto.product.modify.service.ProductModifySvcResDto;
import shop.frankit.domain.product.dto.product.registration.request.ProductRegistrationReqDto;
import shop.frankit.domain.product.dto.product.registration.response.ProductRegistrationResDto;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcResDto;
import shop.frankit.domain.product.dto.productoption.delete.request.ProductOptionDeleteReqDto;
import shop.frankit.domain.product.dto.productoption.delete.response.ProductOptionDeleteResDto;
import shop.frankit.domain.product.dto.productoption.delete.service.ProductOptionDeleteSvcReqDto;
import shop.frankit.domain.product.dto.productoption.delete.service.ProductOptionDeleteSvcResDto;
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


    /**
     * 상품 등록 API
     *
     * @param productRegistrationReqDto
     * @return ProductRegistrationResDto
     */
    @PostMapping("/registration")
    public BaseResponse<ProductRegistrationResDto> registerProduct(@RequestBody @Valid ProductRegistrationReqDto productRegistrationReqDto) {
        ProductRegistrationSvcReqDto serviceRequest = productRegistrationReqDto.toServiceDto();

        User authUser = authService.authenticate();
        ProductRegistrationSvcResDto serviceResponse = productService.saveProduct(authUser, serviceRequest);

        return new BaseResponse<>(ProductRegistrationResDto.from(serviceResponse));
    }


    /**
     * 상품 옵션 INPUT 등록 API
     *
     * @param productOptionInputRegistrationReqDto
     * @return ProductOptionInputRegistrationResDto
     */
    @PostMapping("/registration/input-option")
    public BaseResponse<ProductOptionInputRegistrationResDto> registerInputOption(@RequestBody @Valid ProductOptionInputRegistrationReqDto productOptionInputRegistrationReqDto) {
        ProductOptionInputRegistrationSvcReqDto serviceRequest = productOptionInputRegistrationReqDto.toServiceDto();

        ProductOptionInputRegistrationSvcResDto serviceResponse = productOptionService.addInputOptionToProduct(serviceRequest);

        return new BaseResponse<>(ProductOptionInputRegistrationResDto.from(serviceResponse));
    }

    /**
     * 상품 옵션 SELECT 등록 API
     *
     * @param productOptionSelectRegistrationReqDto
     * @return ProductOptionSelectRegistrationResDto
     */
    @PostMapping("/registration/select-option")
    public BaseResponse<ProductOptionSelectRegistrationResDto> registerSelectOption(@RequestBody @Valid ProductOptionSelectRegistrationReqDto productOptionSelectRegistrationReqDto) {
        ProductOptionSelectRegistrationSvcReqDto serviceRequest = productOptionSelectRegistrationReqDto.toServiceDto();

        ProductOptionSelectRegistrationSvcResDto serviceResponse = productOptionService.addSelectOptionToProduct(serviceRequest);

        return new BaseResponse<>(ProductOptionSelectRegistrationResDto.from(serviceResponse));
    }

    /**
     * 상품 옵션 삭제 API
     *
     * @param productDeleteReqDto
     * @return
     */
    @DeleteMapping("/option")
    public BaseResponse<ProductOptionDeleteResDto> deleteOption(@RequestBody @Valid ProductOptionDeleteReqDto productDeleteReqDto) {
        ProductOptionDeleteSvcReqDto serviceRequest = productDeleteReqDto.toServiceDto();

        ProductOptionDeleteSvcResDto serviceResponse = productOptionService.deleteOption(serviceRequest);

        return new BaseResponse<>(ProductOptionDeleteResDto.from(serviceResponse));
    }


    /**
     * SELECT 옵션 목록 조회 API
     *
     * @return List<CommonOptionDto>
     */
    @GetMapping("/common/option")
    public BaseResponse<List<CommonOptionDto>> viewCommonOption() {
        List<CommonOptionDto> serviceResponse = commonOptionService.getCommonOption();

        return new BaseResponse<>(serviceResponse);
    }

    /**
     * 상품 목록 보기 API
     *
     * @param page
     * @param size
     * @return Page<ProductDto>
     */
    @GetMapping("")
    public BaseResponse<Page<ProductDto>> viewProducts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new BaseResponse<>(productService.getProducts(pageable));
    }

    /**
     * 상품 상세 보기 (Option 목록 포함) API
     *
     * @param productId
     * @return ProductDetailDto
     */
    @GetMapping("/{productId}")
    public BaseResponse<ProductDetailDto> viewProductDetail(@PathVariable(name = "productId") Long productId) {
        return new BaseResponse<>(productService.getProduct(productId));
    }

    /**
     * 상품 변경하기 API
     *
     * @param productModifyReqDto
     * @return ProductModifyResDto
     */
    @PutMapping("")
    public BaseResponse<ProductModifyResDto> modifyProduct(@RequestBody @Valid ProductModifyReqDto productModifyReqDto) {
        ProductModifySvcReqDto serviceRequest = productModifyReqDto.toServiceDto();

        ProductModifySvcResDto serviceResponse = productService.updateProduct(serviceRequest);

        return new BaseResponse<>(ProductModifyResDto.from(serviceResponse));
    }

    @DeleteMapping("")
    public BaseResponse<ProductDeleteResDto> deleteProduct(@RequestBody @Valid ProductDeleteReqDto productDeleteReqDto) {
        ProductDeleteSvcReqDto serviceRequest = productDeleteReqDto.toServiceDto();

        ProductDeleteSvcResDto serviceResponse = productService.deleteProduct(serviceRequest);

        return new BaseResponse<>(ProductDeleteResDto.from(serviceResponse));
    }


}
