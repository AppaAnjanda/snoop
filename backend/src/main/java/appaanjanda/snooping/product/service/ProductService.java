package appaanjanda.snooping.product.service;

import appaanjanda.snooping.product.dto.ProductDetailDto;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    // 상품 정보 조회
    // TODO : 찜목록에 (memberId, productId) 있으면 찜 처리
    public ProductDetailDto getProductById(Long memberId, String index, String productId){
        try {
            // 인덱스랑 id로 상품 정보 요청
            NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.boolQuery()
                            .must(QueryBuilders.termQuery("_index", index))
                            .must(QueryBuilders.termQuery("_id", productId))
                    )
                    .build();

            // 검색 결과
            SearchHits<Map> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, Map.class);

            if (!searchHits.isEmpty()){
                // 상품 기본정보
                SearchHit<Map> hit = searchHits.getSearchHit(0);
                Map<String, Object> productSource = hit.getContent();

                // 최신 가격 정보
                int lastPrice = getLastPrice(index, productId);

                // boolean isWishlist;  // 찜 여부
                return new ProductDetailDto(productSource, lastPrice);
            } else {
                throw new NotFoundException("상품을 찾을 수 없습니다.");
            }
        } catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Elasticsearch 연결 에러");
        }
    }

    // 최신 가격 정보 가져오기
    public int getLastPrice(String index, String productId) throws IOException {
        // "product_123" => "price_123"
        String priceId = "price" + productId.split("_")[1];

        // index, id로 상품 찾아서 시간 내림차순 정렬
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("_index", index))
                        .must(QueryBuilders.termQuery("_id", priceId))
                )
                .withSort(Sort.by(Sort.Order.desc("price_history.timestamp")))
                .build();// 정렬

        // 검색결과
        SearchHits<Map> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, Map.class);

        if (!searchHits.isEmpty()) {
            // 결과값 있으면 첫번째값의 price_history 가져오기
            SearchHit<Map> hit = searchHits.getSearchHit(0);
            Map<String, Object> source = hit.getContent();
            List<Map<String, Object>> priceHistory = (List<Map<String, Object>>) source.getOrDefault("price_history", Collections.emptyList());

            if (!priceHistory.isEmpty()){
                // 첫 번째 원소의 가격 정보를 가져오기, string으로 저장되어 있어서 형 변환
                return (int) priceHistory.get(0).get("price");
            }
        }

        return -1; // 가격 정보가 없는 경우
    }
}
