package appaanjanda.snooping.domain.product.service;

import appaanjanda.snooping.domain.product.entity.product.DigitalProduct;
import appaanjanda.snooping.domain.product.entity.product.FoodProduct;
import appaanjanda.snooping.domain.product.entity.product.FurnitureProduct;
import appaanjanda.snooping.domain.product.entity.product.NecessariesProduct;
import appaanjanda.snooping.domain.product.repository.product.DigitalProductRepository;
import appaanjanda.snooping.domain.product.repository.product.FoodProductRepository;
import appaanjanda.snooping.domain.product.repository.product.FurnitureProductRepository;
import appaanjanda.snooping.domain.product.repository.product.NecessariesProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final DigitalProductRepository digitalProductRepository;
    private final FurnitureProductRepository furnitureProductRepository;
    private final NecessariesProductRepository necessariesProductRepository;
    private final FoodProductRepository foodProductRepository;

    // 상품코드 별 상품 엔티티
    public Class<?> searchProductByIndex(String productCode) {
        // 123 에서 1(대분류 코드) 추출
        char index = productCode.charAt(0);

        switch (index) {
            case '1':
                return DigitalProduct.class;
            case '2':
                return FurnitureProduct.class;
            case '3':
                return NecessariesProduct.class;
            case '4':
                return FoodProduct.class;
            default:
                throw new IllegalArgumentException("Invalid index");
        }
    }

    //상품id로 조회
    // TODO 찜 여부
    public Object searchProductById(String productCode) {
        // 123 에서 1(대분류 코드) 추출
        char index = productCode.charAt(0);

        switch (index) {
            case '1':
                return digitalProductRepository.findByCode(productCode).orElse(null);
            case '2':
                return furnitureProductRepository.findByCode(productCode).orElse(null);
            case '3':
                return necessariesProductRepository.findByCode(productCode).orElse(null);
            case '4':
                return foodProductRepository.findByCode(productCode).orElse(null);
            default:
                throw new IllegalArgumentException("Invalid index");
        }
    }

//    // 상품 정보 검색
//    public SearchHits<?> searchProductById(String index, String productId) {
//        // 반환할 상품 타입
//        Class<?> productType = searchProductByIndex(index);
//
//        try {
//
//            // 인덱스랑 id로 상품 정보 요청
//            NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//                    .withQuery(QueryBuilders.boolQuery()
//                            .must(QueryBuilders.termQuery("_index", index))
//                            .must(QueryBuilders.termQuery("_id", productId))
//                    )
//                    .withSourceFilter(new FetchSourceFilter(null, null))
//                    .build();
//            // 검색 결과
//            return elasticsearchRestTemplate.search(nativeSearchQuery, productType);
//        } catch (Exception e) {
//            throw new RuntimeException("요청 에러", e);
//        }
//    }

}

