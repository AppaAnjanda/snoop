package appaanjanda.snooping.external.logstash.service;


import appaanjanda.snooping.domain.product.entity.price.DigitalPrice;
import appaanjanda.snooping.domain.product.entity.product.DigitalProduct;
import appaanjanda.snooping.domain.product.repository.price.DigitalPriceRepository;
import appaanjanda.snooping.domain.product.repository.product.DigitalProductRepository;
import appaanjanda.snooping.domain.wishbox.service.WishboxService;
import appaanjanda.snooping.external.logstash.entity.ProductInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DigitalDataService {

    private final DigitalProductRepository digitalProductRepository;
    private final DigitalPriceRepository digitalPriceRepository;
    private final WishboxService wishboxService;

    // 최근 업데이트 확인
    public boolean checkUpdateTime(DigitalProduct digitalProduct) {
        log.info("업데이트 체크");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastUpdateTime = LocalDateTime.parse(digitalProduct.getTimestamp());
        // 업데이트 경과 시간
        Duration duration = Duration.between(lastUpdateTime, now);
        // 10분 지났으면 업데이트 진행
        if (duration.toMinutes() >= 10) {
            return true;
        } else return false;
    }

    // 현재 가격과 저장된 가격 비교
    public void checkPrice(ProductInfo productInfo) {
        String currentName = productInfo.getProductName();

        Optional<DigitalProduct> existProduct = digitalProductRepository.findByProductName(currentName);
        // 일치 상품 있는 경우
        if (existProduct.isPresent()) {
            log.info("일치 상품 있음");
            DigitalProduct originProduct = existProduct.get();
            // 최근에 업데이트 되었으면 중단
            if (checkUpdateTime(originProduct)) {

                // 그 시간대의 첫 데이터인지 확인
                LocalDateTime now = LocalDateTime.now();
                int minute = now.getMinute();

                // 정렬 기준
                Sort sort = Sort.by(Sort.Order.desc("@timestamp"));

                // 가격 정보 최신순
                List<DigitalPrice> priceList = digitalPriceRepository.findSortedByCode(productInfo.getCode(), sort);

                // 마지막 가격 정보의 시간
                DigitalPrice lastPrice = priceList.get(0);
                LocalDateTime lastUpdate = LocalDateTime.parse(lastPrice.getTimestamp());

                log.info("업뎃시간 {}", lastUpdate);
                Duration duration = Duration.between(lastUpdate, now);
                log.info("경과시간 {}", duration);
                // 첫타임 데이터 중복 예방
                if (duration.toMinutes() >= 50 && minute < 10) {
                    log.info("첫타임 {}", minute);
                    createPriceData(productInfo, productInfo.getCode());

                }
                // 가격이 바뀌면 업데이트
                if (originProduct.getPrice() != productInfo.getPrice()) {
                    log.info("가격 변동 {}", productInfo.getPrice());
                    updateData(originProduct, productInfo);
                    if (minute >= 10) {
                        updatePriceData(lastPrice, productInfo);
                    }
                }
                String productCode = productInfo.getCode();
                // 찜 여부 판단
                log.info("찜");
                if (wishboxService.checkWishbox(productCode)) {
                    // 알림여부 판단 후 가격 비교하고 알림보내기
                    log.info("알림전송 {}", productCode);
                    wishboxService.checkAlertPrice(productCode, productInfo.getPrice(), productInfo.getProductImage());
                }
            }
        } else {
            // 일치상품 없으면 상품, 가격 둘 다 새로 생성
            log.info("일치상품 없음");
            String newCode = createData(productInfo);
            createPriceData(productInfo, newCode);
        }
    }

    // 새상품 데이터 생성
    public String createData(ProductInfo productInfo) {
        DigitalProduct digitalProduct = new DigitalProduct(productInfo);

        String formatTime = parseTime();

        // 기존 코드는 중복될 수 있으므로 새로 생성
        String newCode = productInfo.getCode().substring(0, 2) + productInfo.getProductName();
        digitalProduct.setCode(newCode);
        digitalProduct.setTimestamp(formatTime);
        digitalProductRepository.save(digitalProduct);
        log.info("새상품 생성 {}", newCode);

        return newCode;
    }

    // 상품 정보 업데이트
    public void updateData(DigitalProduct digitalProduct, ProductInfo productInfo) {

        String formatTime = parseTime();

        // 링크, 출처, 시간, 가격, 이미지 업데이트 후 저장
        digitalProduct.setProductLink(productInfo.getProductLink());
        digitalProduct.setProvider(productInfo.getProvider());
        digitalProduct.setPrice(productInfo.getPrice());
        digitalProduct.setProductImage(productInfo.getProductImage());
        digitalProduct.setTimestamp(formatTime);
        log.info("상품 정보 업데이트 {}", productInfo.getProductLink());

        digitalProductRepository.save(digitalProduct);

    }

    // 그 시간대의 가격 정보 업데이트
    public void updatePriceData(DigitalPrice lastPrice, ProductInfo productInfo) {

        log.info("가격정보 업데이트");

        // 마지막 가격 정보의 가격 업데이트
        lastPrice.setPrice(productInfo.getPrice());
        log.info("그 시간대 가격 업데이트 {}", productInfo.getPrice());

        digitalPriceRepository.save(lastPrice);
    }

    // 가격 정보 생성
    public void createPriceData(ProductInfo productInfo, String productCode) {
        String formatTime = parseTime();

        DigitalPrice digitalPrice = new DigitalPrice(productCode, productInfo.getPrice(), formatTime);

        log.info("새 가격 상품 {}", productInfo.getProductName());
        log.info("새 가격 생성 {}", formatTime);
        log.info("새 가격 {}", productInfo.getPrice());
        digitalPriceRepository.save(digitalPrice);
    }

    public String parseTime() {

        LocalDateTime now = LocalDateTime.now();
        log.info("시간 생성 {}", now);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return now.format(formatter);
    }
}
