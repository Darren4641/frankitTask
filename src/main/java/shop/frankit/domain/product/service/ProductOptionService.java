package shop.frankit.domain.product.service;


import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionInputRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionInputRegistrationSvcResDto;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionSelectRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionSelectRegistrationSvcResDto;
import shop.frankit.domain.user.entity.User;

public interface ProductOptionService {
    ProductOptionInputRegistrationSvcResDto addInputOptionToProduct(User authUser, ProductOptionInputRegistrationSvcReqDto productOptionInputRegistrationSvcReqDto);

    ProductOptionSelectRegistrationSvcResDto addSelectOptionToProduct(User authUser, ProductOptionSelectRegistrationSvcReqDto productOptionSelectRegistrationSvcReqDto);


}
