package appaanjanda.snooping.external.logstash.service;

import appaanjanda.snooping.domain.product.entity.price.FurniturePrice;
import appaanjanda.snooping.domain.product.entity.product.FurnitureProduct;
import appaanjanda.snooping.domain.product.repository.price.FurniturePriceRepository;
import appaanjanda.snooping.domain.product.repository.product.FurnitureProductRepository;
import appaanjanda.snooping.external.logstash.entity.ProductInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.query.SortQueryBuilder;
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
public class FurnitureDataService {

    private final FurnitureProductRepository furnitureProductRepository;
    private final FurniturePriceRepository furniturePriceRepository;

    // 최근 업데이트 확인
    public boolean checkUpdateTime(FurnitureProduct furnitureProduct) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastUpdateTime = LocalDateTime.parse(furnitureProduct.getLastUpdate());
        // 업데이트 경과 시간
        Duration duration = Duration.between(lastUpdateTime, now);
        // 5분 지났으면 업데이트 진행
        if (duration.toMinutes() >= 5) return true;
        else return false;
    }

    // 현재 가격과 저장된 가격 비교
    public void checkPrice(ProductInfo productInfo) {
        String currentName = productInfo.getProductName();

        Optional<FurnitureProduct> existProduct = furnitureProductRepository.findByProductName(currentName);
        // 일치 상품 있는 경우
        if (existProduct.isPresent()) {
            FurnitureProduct originProduct = existProduct.get();
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
        FurnitureProduct furnitureProduct = new FurnitureProduct(productInfo);
        // 기존 코드는 중복될 수 있으므로 새로 생성
        String newCode = productInfo.getCode().substring(0, 2) + productInfo.getProductName();
        furnitureProduct.setCode(newCode);
        furnitureProductRepository.save(furnitureProduct);

        return newCode;
    }

    // 상품 정보 업데이트
    public void updateData(FurnitureProduct furnitureProduct, ProductInfo productInfo) {
        // 링크, 출처, 가격 업데이트 후 저장
        furnitureProduct.setProductLink(productInfo.getProductLink());
        furnitureProduct.setProvider(productInfo.getProvider());
        furnitureProduct.setPrice(productInfo.getPrice());

        furnitureProductRepository.save(furnitureProduct);

    }

    // 그 시간대의 가격 정보 업데이트
    public void updatePriceData(ProductInfo productInfo) {

        // 정렬 기준
        Sort sort = Sort.by(Sort.Order.desc("@timestamp"));
        // 가격 정보 최신순
        List<FurniturePrice> productList = furniturePriceRepository.findSortedByCode(productInfo.getCode(), sort);
        // 마지막 가격 정보의 가격 업데이트
        FurniturePrice lastPrice = productList.get(0);
        lastPrice.setPrice(productInfo.getPrice());

        furniturePriceRepository.save(lastPrice);
    }

    // 가격 정보 생성
    public void createPriceData(ProductInfo productInfo, String productCode) {
        FurniturePrice furniturePrice = new FurniturePrice(productCode, productInfo.getPrice());

        furniturePriceRepository.save(furniturePrice);
    }
}
