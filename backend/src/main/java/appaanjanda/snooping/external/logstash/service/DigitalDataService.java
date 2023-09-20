package appaanjanda.snooping.external.logstash.service;


import appaanjanda.snooping.domain.product.entity.price.DigitalPrice;
import appaanjanda.snooping.domain.product.entity.price.FurniturePrice;
import appaanjanda.snooping.domain.product.entity.product.DigitalProduct;
import appaanjanda.snooping.domain.product.repository.price.DigitalPriceRepository;
import appaanjanda.snooping.domain.product.repository.product.DigitalProductRepository;
import appaanjanda.snooping.external.logstash.entity.ProductInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DigitalDataService {

    private final DigitalProductRepository digitalProductRepository;
    private final DigitalPriceRepository digitalPriceRepository;

    // 최근 업데이트 확인
    public boolean checkUpdateTime(DigitalProduct digitalProduct) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastUpdateTime = LocalDateTime.parse(digitalProduct.getLastUpdate());
        // 업데이트 경과 시간
        Duration duration = Duration.between(lastUpdateTime, now);
        // 5분 지났으면 업데이트 진행
        if (duration.toMinutes() >= 5) return true;
        else return false;
    }

    // 현재 가격과 저장된 가격 비교
    public void checkPrice(ProductInfo productInfo) {
        String currentName = productInfo.getProductName();

        Optional<DigitalProduct> existProduct = digitalProductRepository.findByProductName(currentName);
        // 일치 상품 있는 경우
        if (existProduct.isPresent()) {
            DigitalProduct originProduct = existProduct.get();
            // 최근에 업데이트 되었으면 중단
            if (checkUpdateTime(originProduct)) {

                // 그 시간대의 첫 데이터인지 확인
                LocalDateTime now = LocalDateTime.now();
                int minute = now.getMinute();

                if (minute < 5) {
                    createPriceData(productInfo, productInfo.getCode());

                // 가격이 더 떨어졌으면 업데이트
                } else if (originProduct.getPrice() > productInfo.getPrice()) {
                    updateData(originProduct, productInfo);
                }
            }
        } else {
            // 일치상품 없으면 상품, 가격 둘 다 새로 생성
            String newCode = createData(productInfo);
            createPriceData(productInfo, newCode);
        }
    }

    // 새상품 데이터 생성
    public String createData(ProductInfo productInfo) {
        DigitalProduct digitalProduct = new DigitalProduct(productInfo);
        // 기존 코드는 중복될 수 있으므로 새로 생성
        String newCode = productInfo.getCode().substring(0, 2) + productInfo.getProductName();
        digitalProduct.setCode(newCode);
        digitalProductRepository.save(digitalProduct);

        return newCode;
    }

    // 상품 정보 업데이트
    public void updateData(DigitalProduct digitalProduct, ProductInfo productInfo) {
        // 링크, 출처, 가격 업데이트 후 저장
        digitalProduct.setProductLink(productInfo.getProductLink());
        digitalProduct.setProvider(productInfo.getProvider());
        digitalProduct.setPrice(productInfo.getPrice());

        digitalProductRepository.save(digitalProduct);

    }

    // 그 시간대의 가격 정보 업데이트
    public void updatePriceData(ProductInfo productInfo) {

        // 정렬 기준
        Sort sort = Sort.by(Sort.Order.desc("@timestamp"));
        // 가격 정보 최신순
        List<DigitalPrice> productList = digitalPriceRepository.findSortedByCode(productInfo.getCode(), sort);
        // 마지막 가격 정보의 가격 업데이트
        DigitalPrice lastPrice = productList.get(0);
        lastPrice.setPrice(productInfo.getPrice());

        digitalPriceRepository.save(lastPrice);
    }

    // 가격 정보 생성
    public void createPriceData(ProductInfo productInfo, String productCode) {
        DigitalPrice digitalPrice = new DigitalPrice(productCode, productInfo.getPrice());

        digitalPriceRepository.save(digitalPrice);
    }
}
