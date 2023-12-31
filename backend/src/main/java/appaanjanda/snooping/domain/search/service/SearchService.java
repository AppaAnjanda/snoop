package appaanjanda.snooping.domain.search.service;

import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.domain.member.service.MemberService;
import appaanjanda.snooping.domain.product.entity.product.Product;
import appaanjanda.snooping.domain.product.service.ProductSearchService;
import appaanjanda.snooping.domain.recentProduct.entity.RecentProduct;
import appaanjanda.snooping.domain.search.dto.SearchContentDto;
import appaanjanda.snooping.domain.search.dto.SearchResponseDto;
import appaanjanda.snooping.domain.search.entity.SearchHistory;
import appaanjanda.snooping.domain.search.repository.SearchHistoryRepository;
import appaanjanda.snooping.domain.wishbox.service.WishboxService;
import appaanjanda.snooping.global.error.code.ErrorCode;
import appaanjanda.snooping.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final ProductSearchService productSearchService;
    private final SearchHistoryRepository searchHistoryRepository;
    private final MemberService memberService;
    private final WishboxService wishboxService;

    private final String[] indices = {"디지털가전", "가구", "생활용품", "식품"}; // 검색할 인덱스들

    // 카테고리로 상품 검색
    public SearchResponseDto searchProductByCategory(Long memberId, String major, String minor, int page, int minPrice, int maxPrice) {
        // 반환할 상품 타입
        Class<?> productType = productSearchService.searchEntityByIndex(major);
        // 가격 범위 확인
        if (minPrice < 0 || maxPrice < minPrice || maxPrice >= 100000000) {
            throw new BusinessException(ErrorCode.INVALID_PRICE);
        }

        // 쿼리 작성
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        // 대분류, 소분류 일치하는 상품
                        .must(QueryBuilders.termQuery("major_category.keyword", major))
                        .must(QueryBuilders.termQuery("minor_category.keyword", minor))
                        // 가격범위 필터
                        .filter(QueryBuilders.rangeQuery("price").gte(minPrice).lte(maxPrice))
                )
                .withSourceFilter(new FetchSourceFilter(null, null))
                .withPageable(PageRequest.of(page - 1, 30)) // 30개씩
                .build();
        try{
            // 검색 결과
            SearchHits<Product> searchHits = elasticsearchRestTemplate.search(searchQuery, Product.class, IndexCoordinates.of(indices));
            return searchResponseWithPaging(searchHits, page, memberId);
        } catch (Exception e) {
            throw e;
        }
    }

    // 키워드로 상품 검색
    public SearchResponseDto searchProductByKeyword(String keyword, int page, int minPrice, int maxPrice, Long memberId) {

        // 가격 범위 확인
        if (minPrice < 0 || maxPrice < minPrice || maxPrice >= 100000000) {
            throw new BusinessException(ErrorCode.INVALID_PRICE);
        }

        // 각 단어를 or조건으로 상품명과 소분류에 대해서 match 쿼리로 1차 검색
        MatchQueryBuilder productNameQuery = QueryBuilders.matchQuery("product_name", keyword).fuzziness(Fuzziness.ONE);
        MatchQueryBuilder minorCategoryQuery = QueryBuilders.matchQuery("minor_category", keyword).fuzziness(Fuzziness.ONE);

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(productNameQuery)
                .should(QueryBuilders.matchQuery("product_name", keyword))
                .should(minorCategoryQuery)
                /*
                 * match_phrase로 검색어의 각 단어들을 순서까지 고려하여 완전히 포함하는 상품에 대해 should로 가중치를 줌
                 * slop=1 설정으로 단어 사이 임의의 단어가 1개까지 포함될 수 있다
                 * should 쿼리 최소 하나는 일치하는 항목만
                 */
                .should(QueryBuilders.matchPhraseQuery("product_name", keyword).slop(1))
                .minimumShouldMatch(1)
                // 가격범위 필터
                .filter(QueryBuilders.rangeQuery("price").gte(minPrice).lte(maxPrice));

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withPageable(PageRequest.of(page-1, 30)) // 30개씩
                .build();

        // 모든 인덱스 통합해서 검색
        try {
            SearchHits<Product> searchHits = elasticsearchRestTemplate.search(searchQuery, Product.class, IndexCoordinates.of(indices));
            return searchResponseWithPaging(searchHits, page, memberId);
        }  catch (Exception e) {
            throw e;
        }

    }

    // 검색결과 content 처리 후 페이징 처리
    public SearchResponseDto searchResponseWithPaging(SearchHits<?> searchHits, int page, Long memberId) {

        int total = (int) searchHits.getTotalHits(); // 총 검색 결과 수
        int totalPage = (int) Math.ceil((double) total /30); // 총 페이지 수
        // 검색결과 없음
        if (page > totalPage || total <= 0) {
            throw new BusinessException(ErrorCode.NOT_EXISTS_RESULT);
        }

        // content만 추출해서 전체 결과에 추가
        List<SearchContentDto> contents = searchHits.getSearchHits().stream()
                .map(searchHit -> {
                    // 기존 content
                    Product product = (Product) searchHit.getContent();
                    // 찜 여부
                    boolean wishYn = wishboxService.checkMemberWishbox(product.getCode(), memberId);
                    // Dto 생성
                    return SearchContentDto.builder()
                            .id(product.getId())
                            .code(product.getCode())
                            .majorCategory(product.getMajorCategory())
                            .minorCategory(product.getMinorCategory())
                            .provider(product.getProvider())
                            .price(product.getPrice())
                            .productName(product.getProductName())
                            .productImage(product.getProductImage())
                            .productLink(product.getProductLink())
                            .timestamp(product.getTimestamp())
                            .wishYn(wishYn)
                            .build();
                })
                .collect(Collectors.toList());

        return new SearchResponseDto(contents, page, totalPage);

    }

    // 검색 기록 추가
    public void updateSearchHistory(String keyword, Long memberId) {

        Member member = memberService.findMemberById(memberId);
        // 최근 검색어
        List<SearchHistory> recentKeywords = searchHistoryRepository.findRecentKeywordsOrderByCreateTime(memberId);

        // 최근 검색어중에 현재 검색어가 포함되어 있는지 확인, 없으면 null
        SearchHistory existKeyword = findExistingKeyword(recentKeywords, keyword);

        // 있으면 제거
        if(existKeyword != null) {
            searchHistoryRepository.delete(existKeyword);
        }

        // 새로 검색어 추가
        saveNewSearchHistory(member, keyword);
        
    }
    // 이미 봤던 상품인지 확인
    private SearchHistory findExistingKeyword(List<SearchHistory> recentKeywords, String keyword) {
        return recentKeywords.stream()
                .filter(search -> search.getKeyword().equals(keyword))
                .findFirst()
                .orElse(null);
    }

    private void saveNewSearchHistory(Member member, String keyword) {
        SearchHistory newSearchHistory = new SearchHistory();
        newSearchHistory.setMember(member);
        newSearchHistory.setKeyword(keyword);
        searchHistoryRepository.save(newSearchHistory);
    }

    // 검색 기록 조회
    public List<String> getSearchHistory(Long memberId) {

        List<SearchHistory> searchHistoryList = searchHistoryRepository.findRecentKeywordsOrderByCreateTime(memberId);
        // 최근 검색어 리스트로 변환
        return searchHistoryList.stream()
                .map(SearchHistory::getKeyword)
                .limit(5)
                .collect(Collectors.toList());

    }

    // 검색 기록 삭제
    public void deleteSearchHistory(String keyword, Long memberId) {
        try{
            searchHistoryRepository.deleteByKeywordAndMemberId(keyword, memberId);
        } catch (BusinessException e) {
            throw new BusinessException(ErrorCode.NOT_EXISTS_KEYWORD);
        }
    }
}
