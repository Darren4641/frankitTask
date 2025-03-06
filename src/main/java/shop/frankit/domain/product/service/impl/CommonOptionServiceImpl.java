package shop.frankit.domain.product.service.impl;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.frankit.domain.product.dto.productoptionvalue.CommonOptionDto;
import shop.frankit.domain.product.entity.CommonOption;
import shop.frankit.domain.product.entity.CommonOptionValue;
import shop.frankit.domain.product.repository.commonoption.CommonOptionRepository;
import shop.frankit.domain.product.repository.commonoptionvalue.CommonOptionValueRepository;
import shop.frankit.domain.product.service.CommonOptionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonOptionServiceImpl implements CommonOptionService {
    private final CommonOptionRepository commonOptionRepository;
    private final CommonOptionValueRepository commonOptionValueRepository;

    @PostConstruct
    public void init() {
        // 공통 옵션 1 생성
        if(commonOptionRepository.findByOptionName("의류 사이즈 옵션").isEmpty()) {
            CommonOption commonOption = commonOptionRepository.save(new CommonOption("의류 사이즈 옵션"));
            CommonOptionValue op1 = new CommonOptionValue("S", 0D);
            op1.setCommonOption(commonOption);
            commonOptionValueRepository.save(op1);
            CommonOptionValue op2 = new CommonOptionValue("M", 0D);
            op2.setCommonOption(commonOption);
            commonOptionValueRepository.save(op2);
            CommonOptionValue op3 = new CommonOptionValue("L", 0D);
            op3.setCommonOption(commonOption);
            commonOptionValueRepository.save(op3);
            CommonOptionValue op4 = new CommonOptionValue("XL", 0D);
            op4.setCommonOption(commonOption);
            commonOptionValueRepository.save(op4);
        }
        // 공통 옵션 2 생성
        if(commonOptionRepository.findByOptionName("나무 젓가락 묶음 판매").isEmpty()) {
            CommonOption commonOption = commonOptionRepository.save(new CommonOption("나무 젓가락 묶음 판매"));
            CommonOptionValue op1 = new CommonOptionValue("10 묶음", 500D);
            op1.setCommonOption(commonOption);
            commonOptionValueRepository.save(op1);
            CommonOptionValue op2 = new CommonOptionValue("50 묶음", 2500D);
            op2.setCommonOption(commonOption);
            commonOptionValueRepository.save(op2);
            CommonOptionValue op3 = new CommonOptionValue("100 묶음", 5000D);
            op3.setCommonOption(commonOption);
            commonOptionValueRepository.save(op3);
        }
    }

    @Override
    public List<CommonOptionDto> getCommonOption() {
        return commonOptionRepository.findAll().stream()
                .map(CommonOption::toDto)
                .collect(Collectors.toList());
    }
}
