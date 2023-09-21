package appaanjanda.snooping.domain.product.service;

import appaanjanda.snooping.domain.product.entity.product.DigitalProduct;
import appaanjanda.snooping.domain.product.entity.product.FoodProduct;
import appaanjanda.snooping.domain.product.entity.product.FurnitureProduct;
import appaanjanda.snooping.domain.product.entity.product.NecessariesProduct;
import appaanjanda.snooping.domain.product.entity.product.*;
import appaanjanda.snooping.domain.product.repository.DigitalProductRepository;
import appaanjanda.snooping.domain.product.repository.FoodProductRepository;
import appaanjanda.snooping.domain.product.repository.FurnitureProductRepository;
import appaanjanda.snooping.domain.product.repository.NecessariesProductRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final DigitalProductRepository digitalProductRepository;
    private final FurnitureProductRepository furnitureProductRepository;
    private final NecessariesProductRepository necessariesProductRepository;
    private final FoodProductRepository foodProductRepository;

    //인덱스 별 상품 엔티티
    public Class<?> searchProductByIndex(String index) {
        switch (index) {
            case "디지털가전":
            case"1":
                return DigitalProduct.class;
            case "가구":
            case"2":
                return FurnitureProduct.class;
            case "생활용품":
            case"3":
                return NecessariesProduct.class;
            case "식품":
            case"4":
                return FoodProduct.class;
            default:
                throw new IllegalArgumentException("Invalid index");
        }
    }

    //상품id로 조회
    // TODO 찜 여부
    public Object searchProductById(String productId) {
        // product_123 에서 1(대분류 코드) 추출
        char index = productId.split("_")[1].charAt(0);

        switch (index) {
            case '1':
                return digitalProductRepository.findById(productId).orElse(null);
            case '2':
                return furnitureProductRepository.findById(productId).orElse(null);
            case '3':
                return necessariesProductRepository.findById(productId).orElse(null);
            case '4':
                return foodProductRepository.findById(productId).orElse(null);
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

