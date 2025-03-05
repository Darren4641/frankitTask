package shop.frankit.domain.product.service;

import shop.frankit.domain.product.dto.registration.service.ProductRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.registration.service.ProductRegistrationSvcResDto;

public interface ProductService {
    ProductRegistrationSvcResDto saveProduct(ProductRegistrationSvcReqDto productRegistrationSvcReqDto);
}
