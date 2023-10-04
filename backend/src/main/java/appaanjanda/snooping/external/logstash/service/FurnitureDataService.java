package appaanjanda.snooping.external.logstash.service;

import appaanjanda.snooping.domain.product.entity.price.FoodPrice;
import appaanjanda.snooping.domain.product.entity.price.FurniturePrice;
import appaanjanda.snooping.domain.product.entity.price.NecessariesPrice;
import appaanjanda.snooping.domain.product.entity.product.FurnitureProduct;
import appaanjanda.snooping.domain.product.repository.price.FurniturePriceRepository;
import appaanjanda.snooping.domain.product.repository.product.FurnitureProductRepository;
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
public class FurnitureDataService {


    private final FurnitureProductRepository furnitureProductRepository;
    private final FurniturePriceRepository furniturePriceRepository;
    private final WishboxService wishboxService;


    // 최근 업데이트 확인
    public boolean checkUpdateTime(FurnitureProduct furnitureProduct) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastUpdateTime = LocalDateTime.parse(furnitureProduct.getTimestamp());
        // 업데이트 경과 시간
        Duration duration = Duration.between(lastUpdateTime, now);
        // 10분 지났으면 업데이트 진행
        if (duration.toMinutes() > 9) return true;
        else return false;
    }

    // 현재 가격과 저장된 가격 비교
    public void checkPrice(ProductInfo productInfo) {
        String currentCode = productInfo.getCode();

        Optional<FurnitureProduct> existProduct = furnitureProductRepository.findByCode(currentCode);
        // 일치 상품 있는 경우
        if (existProduct.isPresent()) {
            FurnitureProduct originProduct = existProduct.get();
            currentCode = originProduct.getCode();
            log.info("일치 상품 있음 {}", currentCode);
            // 최근에 업데이트 되었으면 중단
            if (checkUpdateTime(originProduct)) {

                // 그 시간대의 첫 데이터인지 확인
                LocalDateTime now = LocalDateTime.now();
                int minute = now.getMinute();

                // 정렬 기준
                Sort sort = Sort.by(Sort.Order.desc("@timestamp"));

                // 가격 정보 최신순
                List<FurniturePrice> priceList = furniturePriceRepository.findSortedByCode(currentCode, sort);

                log.info(priceList.toString());
                // 마지막 가격 정보의 시간
                FurniturePrice lastPrice = priceList.get(0);
                LocalDateTime lastUpdate = LocalDateTime.parse(lastPrice.getTimestamp());

                Duration duration = Duration.between(lastUpdate, now);
                // 첫타임 데이터 중복 예방
                if (duration.toMinutes() >= 50 && minute < 10) {
                    createPriceData(productInfo, currentCode);
                }
                // 가격이 바뀌면 업데이트
                if (originProduct.getPrice() != productInfo.getPrice()) {
                    log.info("가격 변동 {}", productInfo.getPrice());
                    updateData(originProduct, productInfo);
                    if (minute >= 10) {
                        updatePriceData(lastPrice, productInfo);
                    }
                }

                // 찜 여부 판단
                if (wishboxService.checkWishbox(currentCode)) {
                    // 알림여부 판단 후 가격 비교하고 알림보내기
                    wishboxService.checkAlertPrice(currentCode, productInfo.getPrice(), productInfo.getProductImage());
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
        FurnitureProduct furnitureProduct = new FurnitureProduct(productInfo);

        String formatTime = parseTime();

        // 기존 코드는 중복될 수 있으므로 새로 생성
        String newCode = productInfo.getCode().substring(0, 2) + productInfo.getProductName();
        furnitureProduct.setCode(newCode);
        furnitureProduct.setTimestamp(formatTime);
        furnitureProductRepository.save(furnitureProduct);

        return newCode;
    }

    // 상품 정보 업데이트
    public void updateData(FurnitureProduct furnitureProduct, ProductInfo productInfo) {

        String formatTime = parseTime();

        // 링크, 출처, 시간, 가격, 이미지 업데이트 후 저장
        furnitureProduct.setProductLink(productInfo.getProductLink());
        furnitureProduct.setProvider(productInfo.getProvider());
        furnitureProduct.setPrice(productInfo.getPrice());
        furnitureProduct.setProductImage(productInfo.getProductImage());
        furnitureProduct.setTimestamp(formatTime);

        furnitureProductRepository.save(furnitureProduct);

    }

    // 그 시간대의 가격 정보 업데이트
    public void updatePriceData(FurniturePrice lastPrice, ProductInfo productInfo) {

        // 마지막 가격 정보의 가격 업데이트
        lastPrice.setPrice(productInfo.getPrice());

        furniturePriceRepository.save(lastPrice);

    }

    // 가격 정보 생성
    public void createPriceData(ProductInfo productInfo, String productCode) {

        String formatTime = parseTime();

        FurniturePrice furniturePrice = new FurniturePrice(productCode, productInfo.getPrice(), formatTime);

        furniturePriceRepository.save(furniturePrice);
    }

    public String parseTime() {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return now.format(formatter);
    }
}
