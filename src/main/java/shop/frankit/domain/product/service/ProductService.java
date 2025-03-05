package shop.frankit.domain.product.service;

import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcResDto;
import shop.frankit.domain.user.entity.User;

public interface ProductService {
    ProductRegistrationSvcResDto saveProduct(User authUser, ProductRegistrationSvcReqDto productRegistrationSvcReqDto);
}
