package appaanjanda.snooping.domain.hotProduct.service;

import appaanjanda.snooping.domain.product.service.ProductDetailService;
import appaanjanda.snooping.domain.search.dto.SearchContentDto;
import appaanjanda.snooping.domain.wishbox.entity.Wishbox;
import appaanjanda.snooping.domain.wishbox.repository.WishboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RecommendService {

    private final WishboxRepository wishboxRepository;
    private final RestHighLevelClient restHighLevelClient;
    private final ProductDetailService productDetailService;

    // 찜 기반 추천
    public List<SearchContentDto> recommendByWishbox(Long memberId) throws IOException {

        Set<String> wishProductCode;
        if (memberId != null) {
            wishProductCode = wishboxRepository.findProductById(memberId);
        } else {
            wishProductCode = wishboxRepository.findAllProductCode();
        }
        // 토큰 추출
        List<String> tokenStrings = analyzeProductCode(wishProductCode);
        // 상위 5개 정렬
        StringBuilder sb = sortKeyword(tokenStrings);
        // 상품 반환
        return productDetailService.getSimilarRecommend(String.valueOf(sb), memberId);
    }

    // 상품명 분석
    public List<String> analyzeProductCode(Set<String> wishProductCode) throws IOException {

        // 찜 상품명들을 토크나이저로 분석
        AnalyzeRequest analyzeRequest = AnalyzeRequest.withIndexAnalyzer("snoop", "custom_analyzer",
                wishProductCode.toString());

        AnalyzeResponse response = restHighLevelClient.indices().analyze(analyzeRequest, RequestOptions.DEFAULT);

        List<AnalyzeResponse.AnalyzeToken> analyzeResponse = response.getTokens();

        // token 추출
        List<String> tokenStrings = new ArrayList<>();
        for (AnalyzeResponse.AnalyzeToken token : analyzeResponse) {
            tokenStrings.add(token.getTerm());
        }
        return tokenStrings;
    }

    // 정렬 후 키워드 추출
    public StringBuilder sortKeyword(List<String> tokenStrings) {

        log.info(tokenStrings.toString());
        // 단어 개수 카운트
        HashMap<String, Integer> tokenCounter = new HashMap<>();
        for (String value : tokenStrings) {
            // 있으면 +1, 없으면 만들고 + 1
            tokenCounter.put(value, tokenCounter.getOrDefault(value, 0) + 1);
        }

        // HashMap을 값(value)을 기준으로 상위 10개 추출
        Map<String, Integer> top10 = tokenCounter.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(11)
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new),
                        Collections::unmodifiableMap));

//         결과 출력
        log.info(top10.toString());

        StringBuilder sb = new StringBuilder();
        sb.append("11");
        for (String string : top10.keySet()) {
            if (string.equals("")) continue;
            sb.append(" ").append(string);
        }
//        for (String string : tokenStrings) {
//            if (string.equals("")) continue;
//            sb.append(" ").append(string);
//        }

        log.info(String.valueOf(sb));
        return sb;
    }
}
