package appaanjanda.snooping.external.logstash.service;

import appaanjanda.snooping.domain.product.entity.price.FoodPrice;
import appaanjanda.snooping.domain.product.entity.product.FoodProduct;
import appaanjanda.snooping.domain.product.repository.price.FoodPriceRepository;
import appaanjanda.snooping.domain.product.repository.product.FoodProductRepository;
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
public class FoodDataService {

    private final FoodProductRepository foodProductRepository;
    private final FoodPriceRepository foodPriceRepository;

    // 최근 업데이트 확인
    public boolean checkUpdateTime(FoodProduct foodProduct) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastUpdateTime = LocalDateTime.parse(foodProduct.getTimestamp());
        // 업데이트 경과 시간
        Duration duration = Duration.between(lastUpdateTime, now);
        // 5분 지났으면 업데이트 진행
        if (duration.toMinutes() >= 5) return true;
        else return false;
    }

    // 현재 가격과 저장된 가격 비교
    public void checkPrice(ProductInfo productInfo) {
        String currentName = productInfo.getProductName();

        Optional<FoodProduct> existProduct = foodProductRepository.findByProductName(currentName);
        // 일치 상품 있는 경우
        if (existProduct.isPresent()) {
            FoodProduct originProduct = existProduct.get();
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
                    updatePriceData(productInfo);
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
        FoodProduct foodProduct = new FoodProduct(productInfo);

        String formatTime = parseTime();

        // 기존 코드는 중복될 수 있으므로 새로 생성
        String newCode = productInfo.getCode().substring(0, 2) + productInfo.getProductName();
        foodProduct.setCode(newCode);
        foodProduct.setTimestamp(formatTime);
        foodProductRepository.save(foodProduct);

        return newCode;
    }

    // 상품 정보 업데이트
    public void updateData(FoodProduct foodProduct, ProductInfo productInfo) {

        String formatTime = parseTime();

        // 링크, 출처, 시간, 가격, 이미지 업데이트 후 저장
        foodProduct.setProductLink(productInfo.getProductLink());
        foodProduct.setProvider(productInfo.getProvider());
        foodProduct.setPrice(productInfo.getPrice());
        foodProduct.setProductImage(productInfo.getProductImage());
        foodProduct.setTimestamp(formatTime);

        foodProductRepository.save(foodProduct);

    }

    // 그 시간대의 가격 정보 업데이트
    public void updatePriceData(ProductInfo productInfo) {

        // 정렬 기준
        Sort sort = Sort.by(Sort.Order.desc("@timestamp"));

        // 가격 정보 최신순
        List<FoodPrice> priceList = foodPriceRepository.findSortedByCode(productInfo.getCode(), sort);

        // 마지막 가격 정보의 가격 업데이트
        FoodPrice lastPrice = priceList.get(0);
        lastPrice.setPrice(productInfo.getPrice());

        foodPriceRepository.save(lastPrice);
    }

    // 가격 정보 생성
    public void createPriceData(ProductInfo productInfo, String productCode) {

        String formatTime = parseTime();

        FoodPrice foodPrice = new FoodPrice(productCode, productInfo.getPrice(), formatTime);

        foodPriceRepository.save(foodPrice);
    }

    public String parseTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return now.format(formatter);
    }
}
