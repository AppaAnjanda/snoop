package appaanjanda.snooping.domain.product.service;

import appaanjanda.snooping.domain.product.dto.BuyTimingDto;
import appaanjanda.snooping.domain.product.dto.PriceHistoryDto;
import appaanjanda.snooping.domain.product.entity.price.FoodPrice;
import appaanjanda.snooping.domain.product.entity.price.Price;
import appaanjanda.snooping.domain.product.entity.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.*;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.*;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
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

    private final String[] indices = {"디지털가전가격", "가구가격", "생활용품가격", "식품가격"}; // 검색할 인덱스들

    // 시간별 그래프
    public List<PriceHistoryDto> productGraph(String productCode, DateHistogramInterval interval, int cnt) {
        Class<?> productType = productSearchService.searchPriceById(productCode);

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
                .withPageable(PageRequest.of(0, 1))
                .build();

        try {
            SearchHits<?> searchHits = elasticsearchRestTemplate.search(searchQuery, productType);
            return transferAggregation(searchHits, cnt);
        } catch(Exception e){
            throw e;
        }
    }

    // aggregation 추출해서 dto 변환
    public List<PriceHistoryDto> transferAggregation(SearchHits<?> searchHits, int cnt) {
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
            if (result.size() >= cnt) break; // 일정 기간까지

        }
        return result;
    }

    // 평균가 집계 쿼리
    public SearchHits<?> getAvgPrice(String productCode) {
        Class<?> productType = productSearchService.searchPriceById(productCode);

        // 평균가격 구하기
        AvgAggregationBuilder avgAggregation = AggregationBuilders.avg("avg_price")
                .field("price");

        // 상품코드 일치하는 제품의 최근 30일간의 데이터
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("code.keyword", productCode))
                .filter(QueryBuilders.rangeQuery("@timestamp").gte("now-30d/d").lte("now+1d/d"));

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .addAggregation(avgAggregation)
                .withPageable(PageRequest.of(0, 1))
                .withSort(SortBuilders.fieldSort("@timestamp").order(SortOrder.DESC))
                .build();

        return elasticsearchRestTemplate.search(searchQuery, Price.class, IndexCoordinates.of(indices));
    }

    public BuyTimingDto buyTiming(String productCode) {
        // 평균가격 집계 결과
        SearchHits<?> searchHits = getAvgPrice(productCode);

        // 마지막 데이터에서 현재 가격 가져오기
        Price price = (Price) searchHits.getSearchHits().get(0).getContent();
        int curPrice = price.getPrice();

        // 평균가격
        ParsedAvg avgPriceAggregation = searchHits.getAggregations().get("avg_price");
        int avgPrice = (int) avgPriceAggregation.getValue();

        String[] info = checkTiming(avgPrice, curPrice);
        double diffPercent = Double.parseDouble(info[0]);
        String timing = info[1];
        // 정보 추가
        return new BuyTimingDto(avgPrice, curPrice, diffPercent, timing);

    }

    // 구매 타이밍 계산
    public String[] checkTiming(int avgPrice, int curPrice) {

        // 평균에서 할인률
        double diffPricePercent = ((double) (curPrice - avgPrice) / avgPrice) * 100;
        // 소수 둘째에서 반올림
        double roundedPercent = Math.round(diffPricePercent * 10.0) / 10.0;

        String timing;
        // 타이밍
        if (roundedPercent <= -30) timing = "완전 싸다";
        else if (roundedPercent <= -15) timing = "싸다";
        else if (roundedPercent <= -5) timing = "약간 싸다";
        else if (roundedPercent < 5) timing = "보통";
        else if (roundedPercent < 15) timing = "약간 비싸다";
        else if (roundedPercent < 30) timing = "비싸다";
        else timing = "완전 비싸다";

        return new String[]{String.valueOf(roundedPercent), timing};
    }

}
