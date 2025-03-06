package shop.frankit.domain.product.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.product.registration.service.ProductRegistrationSvcResDto;
import shop.frankit.domain.product.entity.Product;
import shop.frankit.domain.product.repository.product.ProductRepository;
import shop.frankit.domain.user.TestUserUtil;
import shop.frankit.domain.user.entity.User;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;


    @Test
    @DisplayName("상품 등록 - 성공적으로 저장")
    void saveProduct_Success() {
        // given
        User authUser = TestUserUtil.getAuthUser();
        ProductRegistrationSvcReqDto productRequest = new ProductRegistrationSvcReqDto("test", "test", 1000D, 500D);
        Product productEntity = productRequest.toEntity(authUser);

        given(productRepository.save(any(Product.class))).willReturn(productEntity);

        // when
        ProductRegistrationSvcResDto response = productService.saveProduct(authUser, productRequest);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(productEntity.getName());
        assertThat(response.getDescription()).isEqualTo(productEntity.getDescription());
        assertThat(response.getPrice()).isEqualTo(productEntity.getPrice());
        assertThat(response.getDeliveryFee()).isEqualTo(productEntity.getDeliveryFee());

        verify(productRepository).save(any(Product.class));
    }

}