package appaanjanda.snooping.domain.search.service;

import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.domain.member.service.MemberService;
import appaanjanda.snooping.domain.product.service.ProductSearchService;
import appaanjanda.snooping.domain.search.entity.SearchHistory;
import appaanjanda.snooping.domain.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final ProductSearchService productSearchService;
    private final SearchRepository searchRepository;
    private final MemberService memberService;

    // 카테고리로 상품 검색
    // TODO 페이징 처리
    public List<?> searchProductByCategory(String index, String minor) {
        // 반환할 상품 타입
        Class<?> productType = productSearchService.searchProductByIndex(index);

        // 쿼리 작성
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("_index", index))
                        .must(QueryBuilders.termQuery("minor_category.keyword", minor))
                )
                .withSourceFilter(new FetchSourceFilter(null, null))
                .withPageable(Pageable.ofSize(100)) // 100 개
                .build();

        // 검색 결과
        SearchHits<?> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, productType);

        // content만 추출
        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

    }

    // 키워드로 상품 검색
    public List<?> searchProductByKeyword(String keyword) {
        List<Object> totalResults = new ArrayList<>();
        String[] indices = {"디지털가전", "가구", "생활용품", "식품"}; // 검색할 인덱스들

        for (String index : indices) {
            Class<?> productType = productSearchService.searchProductByIndex(index);

            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.fuzzyQuery("product_name", keyword))
                    .build();

            SearchHits<?> searchHits = elasticsearchRestTemplate.search(searchQuery, productType);

            // content만 추출해서 전체 결과에 추가
            totalResults.addAll(
                    searchHits.getSearchHits().stream()
                            .map(SearchHit::getContent)
                            .collect(Collectors.toList())
            );
        }
        return totalResults;
    }

    // 검색 기록 추가
    public void updateSearchHistory(String keyword, Long memberId) {

        Member member = memberService.findMemberById(memberId);
        // 최근 검색어
        List<SearchHistory> recentKeywords = searchRepository.findRecentKeywordsOrderByCreateTime(memberId);

        // 최근 검색어중에 현재 검색어가 포함되어 있는지 확인, 없으면 null
        SearchHistory existKeyword = recentKeywords.stream()
                .filter(search -> search.getKeyword().equals(keyword))
                .findFirst()
                .orElse(null);

        // 있으면 제거
        if(existKeyword != null) {
            searchRepository.delete(existKeyword);
            recentKeywords.remove(existKeyword);
        }

        // 새로 검색어 추가
        SearchHistory newSearchHistory = new SearchHistory();
        newSearchHistory.setMember(member);
        newSearchHistory.setKeyword(keyword);
        searchRepository.save(newSearchHistory);

        // 최근 검색어가 5개 이상이면 마지막꺼 지우고 추가
        if (recentKeywords.size() >= 5) {
            SearchHistory oldestKeyword = recentKeywords.get(recentKeywords.size()-1);
            searchRepository.delete(oldestKeyword);
        }
    }

    // 검색 기록 조회
    public List<String> getSearchHistory(Long memberId) {

        List<SearchHistory> searchHistoryList = searchRepository.findRecentKeywordsOrderByCreateTime(memberId);
        // 최근 검색어 리스트로 변환
        return searchHistoryList.stream()
                .map(SearchHistory::getKeyword)
                .collect(Collectors.toList());

    }
}
