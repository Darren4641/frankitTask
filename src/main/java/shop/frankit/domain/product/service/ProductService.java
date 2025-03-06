package shop.frankit.domain.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.frankit.domain.product.dto.product.delete.service.ProductDeleteSvcReqDto;
import shop.frankit.domain.product.dto.product.delete.service.ProductDeleteSvcResDto;
import shop.frankit.domain.product.dto.product.detail.ProductDetailDto;
import shop.frankit.domain.product.dto.product.info.ProductDto;
import shop.frankit.domain.product.dto.product.modify.service.ProductModifySvcReqDto;
import shop.frankit.domain.product.dto.product.modify.service.ProductModifySvcResDto;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcResDto;
import shop.frankit.domain.user.entity.User;

public interface ProductService {
    ProductRegistrationSvcResDto saveProduct(User authUser, ProductRegistrationSvcReqDto productRegistrationSvcReqDto);

    Page<ProductDto> getProducts(Pageable pageable);

    ProductDetailDto getProduct(Long productId);

    ProductModifySvcResDto updateProduct(ProductModifySvcReqDto productModifySvcReqDto);

    ProductDeleteSvcResDto deleteProduct(ProductDeleteSvcReqDto productDeleteSvcReqDto);
}
