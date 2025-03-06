package shop.frankit.domain.product.service;


import shop.frankit.domain.product.dto.productoptionvalue.CommonOptionDto;

import java.util.List;

public interface CommonOptionService {
    List<CommonOptionDto> getCommonOption();
}
