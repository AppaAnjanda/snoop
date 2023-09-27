package appaanjanda.snooping.domain.hotProduct.service;

import appaanjanda.snooping.domain.search.dto.SearchContentDto;
import appaanjanda.snooping.domain.wishbox.entity.Wishbox;
import appaanjanda.snooping.domain.wishbox.repository.WishboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.xcontent.ToXContent;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RecommendService {

    private final WishboxRepository wishboxRepository;
    private final RestHighLevelClient restHighLevelClient;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    // 찜 기반 추천
    public List<SearchContentDto> recommendByWishbox(Long memberId) throws IOException {

        Set<String> wishProductCode;
        if (memberId != null) {
            wishProductCode = wishboxRepository.findProductById(memberId);
        } else {
            wishProductCode = wishboxRepository.findAllProductCode();
        }
        analyzeProductCode(wishProductCode);
        return null;
    }

    // 상품명 분석
    public void analyzeProductCode(Set<String> wishProductCode) throws IOException {


        AnalyzeRequest analyzeRequest = AnalyzeRequest.withGlobalAnalyzer("snoop",
                "11LG전자 2023 LED QNED 4K 189cm (75QNED80KRA)", "12초미니냉장고/소형냉장고/미니냉장고/화장품냉장고/차량용 화장품 냉장고, 10L실버");

        XContentBuilder builder = XContentFactory.jsonBuilder();
    }
}
