package appaanjanda.snooping.domain.product.service;

import appaanjanda.snooping.domain.product.dto.PriceHistoryDto;
import appaanjanda.snooping.domain.product.entity.price.Price;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductDetailService {

    private final ProductSearchService productSearchService;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    // 시간별 그래프
    public List<PriceHistoryDto> productGraph(String productCode, DateHistogramInterval interval, int cnt) {
        Class<?> productType = productSearchService.searchPriceById(productCode);
        // 각 버킷의 최저값
        AggregationBuilder minAggregation = AggregationBuilders.min("min_price")
                .field("price");

        // interval별로 버킷 생성하고 하위 집계 추가
        AbstractAggregationBuilder<?> dateMinAggregation = AggregationBuilders.dateHistogram("price_history")
                .field("@timestamp")
                .calendarInterval(interval)
                .subAggregation(minAggregation);


        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("code.keyword", productCode))
                .withAggregations(dateMinAggregation)
                .build();

        SearchHits<?> searchHits = elasticsearchRestTemplate.search(searchQuery, productType);

        List<PriceHistoryDto> priceHistory = searchHits.getSearchHits().stream()
                .map(searchHit -> {
                    Price price = (Price) searchHit.getContent();
                    return PriceHistoryDto.builder()
                            .timestamp(LocalDateTime.parse(price.getTimestamp()))
                            .price(price.getPrice())
                            .build();
                })
                .collect(Collectors.toList());

        return priceHistory;
    }
}
