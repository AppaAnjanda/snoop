package appaanjanda.snooping.external.logstash.service;

import appaanjanda.snooping.domain.product.entity.price.NecessariesPrice;
import appaanjanda.snooping.domain.product.entity.product.NecessariesProduct;
import appaanjanda.snooping.domain.product.repository.price.NecessariesPriceRepository;
import appaanjanda.snooping.domain.product.repository.product.NecessariesProductRepository;
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
public class NecessariesDataService {

    // 현재시간
    private final LocalDateTime now = LocalDateTime.now();

    private final NecessariesProductRepository necessariesProductRepository;
    private final NecessariesPriceRepository necessariesPriceRepository;

    // 최근 업데이트 확인
    public boolean checkUpdateTime(NecessariesProduct necessariesProduct) {

        LocalDateTime lastUpdateTime = LocalDateTime.parse(necessariesProduct.getTimestamp());
        LocalDateTime realTime = lastUpdateTime.plusHours(9);
        // 업데이트 경과 시간
        Duration duration = Duration.between(realTime, now);
        // 5분 지났으면 업데이트 진행
        if (duration.toMinutes() >= 10) return true;
        else return false;
    }

    // 현재 가격과 저장된 가격 비교
    public void checkPrice(ProductInfo productInfo) {
        String currentName = productInfo.getProductName();

        Optional<NecessariesProduct> existProduct = necessariesProductRepository.findByProductName(currentName);
        // 일치 상품 있는 경우
        if (existProduct.isPresent()) {
            NecessariesProduct originProduct = existProduct.get();
            // 최근에 업데이트 되었으면 중단
            if (checkUpdateTime(originProduct)) {

                // 그 시간대의 첫 데이터인지 확인
                int minute = now.getMinute();

                if (minute < 10) {
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
        NecessariesProduct necessariesProduct = new NecessariesProduct(productInfo);

        String formatTime = parseTime();

        // 기존 코드는 중복될 수 있으므로 새로 생성
        String newCode = productInfo.getCode().substring(0, 2) + productInfo.getProductName();
        necessariesProduct.setCode(newCode);
        necessariesProduct.setTimestamp(formatTime);
        necessariesProductRepository.save(necessariesProduct);

        return newCode;
    }

    // 상품 정보 업데이트
    public void updateData(NecessariesProduct necessariesProduct, ProductInfo productInfo) {

        String formatTime = parseTime();

        // 링크, 출처, 시간, 가격, 이미지 업데이트 후 저장
        necessariesProduct.setProductLink(productInfo.getProductLink());
        necessariesProduct.setProvider(productInfo.getProvider());
        necessariesProduct.setPrice(productInfo.getPrice());
        necessariesProduct.setProductImage(productInfo.getProductImage());
        necessariesProduct.setTimestamp(formatTime);

        necessariesProductRepository.save(necessariesProduct);

    }

    // 그 시간대의 가격 정보 업데이트
    public void updatePriceData(ProductInfo productInfo) {

        int minute = now.getMinute();

        if (minute < 10) {

            // 정렬 기준
            Sort sort = Sort.by(Sort.Order.desc("@timestamp"));

            // 가격 정보 최신순
            List<NecessariesPrice> priceList = necessariesPriceRepository.findSortedByCode(productInfo.getCode(), sort);

            // 마지막 가격 정보의 가격 업데이트
            NecessariesPrice lastPrice = priceList.get(0);
            lastPrice.setPrice(productInfo.getPrice());

            necessariesPriceRepository.save(lastPrice);
        }
    }

    // 가격 정보 생성
    public void createPriceData(ProductInfo productInfo, String productCode) {

        String formatTime = parseTime();

        NecessariesPrice necessariesPrice = new NecessariesPrice(productCode, productInfo.getPrice(), formatTime);

        necessariesPriceRepository.save(necessariesPrice);
    }

    public String parseTime() {

        LocalDateTime realTime = now.minusHours(9);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return realTime.format(formatter);
    }
}
