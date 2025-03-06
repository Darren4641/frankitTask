package shop.frankit.domain.product.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.frankit.common.exception.ApiErrorException;
import shop.frankit.common.response.ResultCode;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionInputRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionInputRegistrationSvcResDto;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionSelectRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionSelectRegistrationSvcResDto;
import shop.frankit.domain.product.entity.CommonOption;
import shop.frankit.domain.product.entity.Product;
import shop.frankit.domain.product.entity.ProductOption;
import shop.frankit.domain.product.repository.commonoption.CommonOptionRepository;
import shop.frankit.domain.product.repository.product.ProductRepository;
import shop.frankit.domain.product.service.ProductOptionService;
import shop.frankit.domain.user.entity.User;

@Service
@RequiredArgsConstructor
public class ProductOptionServiceImpl implements ProductOptionService {
    private final ProductRepository productRepository;
    private final CommonOptionRepository commonOptionRepository;

    @Override
    @Transactional
    public ProductOptionInputRegistrationSvcResDto addInputOptionToProduct(User authUser, ProductOptionInputRegistrationSvcReqDto productOptionInputRegistrationSvcReqDto) {
        Product productEntity = productRepository.findByIdDsl(authUser.getId(), productOptionInputRegistrationSvcReqDto.getProductId())
                .orElseThrow(() -> new ApiErrorException(ResultCode.NOT_FOUND));

        long optionCount = productRepository.countProductOptions(productEntity.getId());

        if (optionCount >= 3) {
            throw new ApiErrorException(ResultCode.LIMIT_EXCEEDED);
        }

        ProductOption productOptionEntity = productOptionInputRegistrationSvcReqDto.toEntity(productEntity);

        return ProductOptionInputRegistrationSvcResDto.fromEntity(productEntity.getId(), productOptionEntity);
    }

    @Override
    @Transactional
    public ProductOptionSelectRegistrationSvcResDto addSelectOptionToProduct(User authUser, ProductOptionSelectRegistrationSvcReqDto productOptionSelectRegistrationSvcReqDto) {
        // Product 조회
        Product productEntity = productRepository.findByIdDsl(authUser.getId(), productOptionSelectRegistrationSvcReqDto.getProductId())
                .orElseThrow(() -> new ApiErrorException(ResultCode.NOT_FOUND));

        // CommonOption 조회
        CommonOption optionEntity = commonOptionRepository.findById(productOptionSelectRegistrationSvcReqDto.getOptionId())
                .orElseThrow(() -> new ApiErrorException(ResultCode.NOT_FOUND));

        long optionCount = productRepository.countProductOptions(productEntity.getId());

        if (optionCount >= 3) {
            throw new ApiErrorException(ResultCode.LIMIT_EXCEEDED);
        }

        ProductOption productOptionEntity = productOptionSelectRegistrationSvcReqDto.toEntity(productEntity, optionEntity);

        return ProductOptionSelectRegistrationSvcResDto.fromEntity(productEntity.getId(), productOptionEntity);
    }
}
