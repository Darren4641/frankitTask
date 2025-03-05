package shop.frankit.domain.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.frankit.domain.product.dto.registration.service.ProductRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.registration.service.ProductRegistrationSvcResDto;
import shop.frankit.domain.product.repository.ProductRepository;
import shop.frankit.domain.product.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ProductRegistrationSvcResDto saveProduct(ProductRegistrationSvcReqDto productRegistrationSvcReqDto) {

        return null;
    }
}
