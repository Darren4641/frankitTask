package shop.frankit.domain.product.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.frankit.common.exception.ApiErrorException;
import shop.frankit.common.response.ResultCode;
import shop.frankit.domain.product.dto.productoption.service.ProductOptionRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.productoption.service.ProductOptionRegistrationSvcResDto;
import shop.frankit.domain.product.entity.Product;
import shop.frankit.domain.product.entity.ProductOption;
import shop.frankit.domain.product.repository.ProductRepository;
import shop.frankit.domain.product.service.ProductOptionService;
import shop.frankit.domain.user.entity.User;

@Service
@RequiredArgsConstructor
public class ProductOptionServiceImpl implements ProductOptionService {
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductOptionRegistrationSvcResDto addOptionToProduct(User authUser, ProductOptionRegistrationSvcReqDto productOptionRegistrationSvcReqDto) {
        Product productEntity = productRepository.findByIdDsl(authUser.getId(), productOptionRegistrationSvcReqDto.getProductId())
                .orElseThrow(() -> new ApiErrorException(ResultCode.NOT_FOUND));

        long optionCount = productRepository.countProductOptions(productEntity.getId());

        if (optionCount >= 3) {
            throw new ApiErrorException(ResultCode.LIMIT_EXCEEDED);
        }

        ProductOption productOptionEntity = productOptionRegistrationSvcReqDto.toEntity(productEntity);

        return ProductOptionRegistrationSvcResDto.fromEntity(productEntity.getId(), productOptionEntity);
    }
}
