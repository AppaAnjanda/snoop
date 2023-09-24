package appaanjanda.snooping.domain.product.service;

import appaanjanda.snooping.domain.product.dto.PriceHistoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.*;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.aggregations.metrics.Min;
import org.elasticsearch.search.aggregations.metrics.MinAggregationBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductDetailService {

    private final ProductSearchService productSearchService;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    // 시간별 그래프
    public List<PriceHistoryDto> productGraph(String productCode, DateHistogramInterval interval, int cnt) {
        Class<?> productType = productSearchService.searchPriceById(productCode);
        log.info(String.valueOf(interval));
        log.info(String.valueOf(cnt));
        // 각 버킷의 최저값
        MinAggregationBuilder minAggregation = AggregationBuilders.min("min_price")
                .field("price");

        // interval별로 버킷 생성하고 하위 집계 추가, 내림차순
        DateHistogramAggregationBuilder dateMinAggregation = AggregationBuilders.dateHistogram("price_history")
                .field("@timestamp")
                .calendarInterval(interval)
                .subAggregation(minAggregation)
                .order(BucketOrder.key(false));

//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder()
//                .query(QueryBuilders.termQuery("code.keyword", productCode))
//                .aggregation(dateMinAggregation)
//                .size(30);
        // code일치하는 상품
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("code.keyword", productCode))
                .addAggregation(dateMinAggregation)
                .build();

        try {
            SearchHits<?> searchHits = elasticsearchRestTemplate.search(searchQuery, productType);
            return transferAggregation(searchHits);
        } catch(Exception e){
            throw e;
        }
    }

    // aggregation 추출해서 dto 변환
    public List<PriceHistoryDto> transferAggregation(SearchHits<?> searchHits) {
        // 상위 집계 추출
        ParsedDateHistogram groupByDateHistogram = searchHits.getAggregations().get("price_history");
        // 반환할 리스트
        List<PriceHistoryDto> result = new ArrayList<>();
        // 집계의 각 버킷 순회
        for (Histogram.Bucket bucket : groupByDateHistogram.getBuckets()){
            PriceHistoryDto priceHistoryDto = new PriceHistoryDto();
            // 시간 추출
            String timestamp = bucket.getKeyAsString();
            priceHistoryDto.setTimestamp(ZonedDateTime.parse(timestamp, DateTimeFormatter.ISO_OFFSET_DATE_TIME).toLocalDateTime());

            // 하위집계에서 최저가 추출
            Min minPrice = (Min) bucket.getAggregations().asMap().get("min_price");
            int minValue = (int) minPrice.getValue();
            priceHistoryDto.setPrice(minValue);

            result.add(priceHistoryDto);

        }
        return result;
    }
}
