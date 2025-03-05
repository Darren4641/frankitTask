package shop.frankit.domain.product.service;


import shop.frankit.domain.product.dto.productoption.service.ProductOptionRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.productoption.service.ProductOptionRegistrationSvcResDto;
import shop.frankit.domain.user.entity.User;

public interface ProductOptionService {
    ProductOptionRegistrationSvcResDto addOptionToProduct(User authUser, ProductOptionRegistrationSvcReqDto productOptionRegistrationSvcReqDto);
}
