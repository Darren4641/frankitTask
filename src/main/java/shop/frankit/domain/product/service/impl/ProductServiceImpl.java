package shop.frankit.domain.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.frankit.common.exception.ApiErrorException;
import shop.frankit.common.response.ResultCode;
import shop.frankit.domain.product.dto.product.delete.service.ProductDeleteSvcReqDto;
import shop.frankit.domain.product.dto.product.delete.service.ProductDeleteSvcResDto;
import shop.frankit.domain.product.dto.product.detail.ProductDetailDto;
import shop.frankit.domain.product.dto.product.detail.ProductOptionDetailDto;
import shop.frankit.domain.product.dto.product.info.ProductDto;
import shop.frankit.domain.product.dto.product.modify.service.ProductModifySvcReqDto;
import shop.frankit.domain.product.dto.product.modify.service.ProductModifySvcResDto;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcResDto;
import shop.frankit.domain.product.entity.Product;
import shop.frankit.domain.product.repository.product.ProductRepository;
import shop.frankit.domain.product.service.ProductService;
import shop.frankit.domain.user.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductRegistrationSvcResDto saveProduct(User authUser, ProductRegistrationSvcReqDto productRegistrationSvcReqDto) {
        Product productEntity = productRepository.save(productRegistrationSvcReqDto.toEntity(authUser));

        return ProductRegistrationSvcResDto.fromEntity(productEntity);
    }

    @Override
    public Page<ProductDto> getProducts(Pageable pageable) {
        return productRepository.findProductsPageDsl(pageable);
    }

    @Override
    public ProductDetailDto getProduct(Long productId) {
        ProductDetailDto productDetailDto = productRepository.findProductDsl(productId)
                .orElseThrow(() -> new ApiErrorException(ResultCode.NOT_FOUND));

        List<ProductOptionDetailDto> options = productRepository.getOptions(productId);
        productDetailDto.addOptions(options);

        return productDetailDto;
    }

    @Override
    @Transactional
    public ProductModifySvcResDto updateProduct(ProductModifySvcReqDto productModifySvcReqDto) {
        Product productEntity = productRepository.findById(productModifySvcReqDto.getProductId())
                .orElseThrow(() -> new ApiErrorException(ResultCode.NOT_FOUND));

        productEntity.update(productModifySvcReqDto);

        return ProductModifySvcResDto.fromEntity(productEntity);
    }

    @Override
    @Transactional
    public ProductDeleteSvcResDto deleteProduct(ProductDeleteSvcReqDto productDeleteSvcReqDto) {
        Product productEntity = productRepository.findById(productDeleteSvcReqDto.getProductId())
                .orElseThrow(() -> new ApiErrorException(ResultCode.NOT_FOUND));

        productRepository.delete(productEntity);

        return ProductDeleteSvcResDto.fromEntity(productEntity);
    }
}
