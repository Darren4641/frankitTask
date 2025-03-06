package shop.frankit.domain.product.service;


import shop.frankit.domain.product.dto.productoption.delete.service.ProductOptionDeleteSvcReqDto;
import shop.frankit.domain.product.dto.productoption.delete.service.ProductOptionDeleteSvcResDto;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionInputRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionInputRegistrationSvcResDto;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionSelectRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionSelectRegistrationSvcResDto;
import shop.frankit.domain.user.entity.User;

public interface ProductOptionService {
    ProductOptionInputRegistrationSvcResDto addInputOptionToProduct(ProductOptionInputRegistrationSvcReqDto productOptionInputRegistrationSvcReqDto);

    ProductOptionSelectRegistrationSvcResDto addSelectOptionToProduct(ProductOptionSelectRegistrationSvcReqDto productOptionSelectRegistrationSvcReqDto);

    ProductOptionDeleteSvcResDto deleteOption(ProductOptionDeleteSvcReqDto productOptionDeleteSvcReqDto);


}
