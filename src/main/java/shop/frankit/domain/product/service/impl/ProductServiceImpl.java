package shop.frankit.domain.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcResDto;
import shop.frankit.domain.product.entity.Product;
import shop.frankit.domain.product.repository.product.ProductRepository;
import shop.frankit.domain.product.service.ProductService;
import shop.frankit.domain.user.entity.User;

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
}
