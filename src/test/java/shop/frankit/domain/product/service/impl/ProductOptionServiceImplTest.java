package shop.frankit.domain.product.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.frankit.domain.product.dto.OptionType;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionInputRegistrationSvcReqDto;
import shop.frankit.domain.product.dto.productoption.registration.service.ProductOptionInputRegistrationSvcResDto;
import shop.frankit.domain.product.entity.Product;
import shop.frankit.domain.product.repository.product.ProductRepository;
import shop.frankit.domain.user.TestUserUtil;
import shop.frankit.domain.user.entity.User;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductOptionServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductOptionServiceImpl productOptionService;

    @Test
    @DisplayName("옵션 등록 - 성공적으로 저장")
    void saveProductOption_Success() {
        // given
        User authUser = TestUserUtil.getAuthUser();
        Product productEntity = new Product(1L, "test", "test", 1000D, 500D);
        productEntity.setUser(authUser);
        ProductOptionInputRegistrationSvcReqDto optionRequest = new ProductOptionInputRegistrationSvcReqDto(productEntity.getId(), "op1", OptionType.INPUT, 100D);

        given(productRepository.findByIdDsl(productEntity.getId()))
                .willReturn(Optional.of(productEntity));

        given(productRepository.countProductOptions(productEntity.getId()))
                .willReturn(2L);  // 현재 옵션이 2개 있다고 가정

        // when
        ProductOptionInputRegistrationSvcResDto response = productOptionService.addInputOptionToProduct(optionRequest);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getProductId()).isEqualTo(productEntity.getId());
        assertThat(response.getOptionName()).isEqualTo(optionRequest.getOptionName());

        verify(productRepository).findByIdDsl(productEntity.getId());
        verify(productRepository).countProductOptions(productEntity.getId());
    }
}