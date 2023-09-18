package appaanjanda.snooping.domain.product.service;

import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.domain.member.service.MemberService;
import appaanjanda.snooping.domain.product.dto.ProductDetailDto;
import appaanjanda.snooping.domain.product.entity.RecentProduct;
import appaanjanda.snooping.domain.product.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductService {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final ProductSearchService productSearchService;
    private final MemberService memberService;
    private final RecentProductRepository recentProductRepository;


//    // 상품 정보 조회
//    // TODO : 찜목록에 (memberId, productId) 있으면 찜 처리, 최근 본 상품에 추가
//    public ProductDetailDto getProductById(Long memberId, String index, String productId){
//
//        SearchHits<?> searchHits = productSearchService.searchProductById(index, productId);
//        Object searchProduct;
//
//        // 첫번째 객체 가져오기
//        if (!searchHits.isEmpty()) {
//            // 상품 기본정보
//            SearchHit<?> hit = searchHits.getSearchHit(0);
//            searchProduct =  hit.getContent();
//
//        } else{
//            throw new NotFoundException("상품을 찾을 수 없습니다.");
//        }
//
//        // boolean isWishlist;  // 찜 여부
//
//        return new ProductDetailDto(searchProduct);
//
//    }

    // 최근 본 상품 추가
    public void updateRecentProduct(Long memberId, String productId) {

        Member member = memberService.findMemberById(memberId);
        // 최근 본 상품
        List<RecentProduct> recentProducts = recentProductRepository.findRecentProductsOrderByCreateTime(memberId);

        // 최근 본 상품에 현재 상품이 포함되어 있는지 확인, 없으면 null
        RecentProduct existProduct = recentProducts.stream()
                .filter(search -> search.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        // 있으면 제거
        if(existProduct != null) {
            recentProductRepository.delete(existProduct);
            recentProducts.remove(existProduct);
        }

        // 새로 본 상품 추가
        RecentProduct newWatchProduct = new RecentProduct();
        newWatchProduct.setMember(member);
        newWatchProduct.setProductId(productId);
        recentProductRepository.save(newWatchProduct);

        // 최근 검색어가 10개 이상이면 마지막꺼 지우고 추가
        if (recentProducts.size() >= 10) {
            RecentProduct oldestProduct = recentProducts.get(recentProducts.size()-1);
            recentProductRepository.delete(oldestProduct);
        }

    }

    // 최근 본 상품 리스트
    public List<Object> getRecentProduct(Long memberId) {
        List<RecentProduct> recentProducts = recentProductRepository.findRecentProductsOrderByCreateTime(memberId);

        List<Object> products = new ArrayList<>();

        for (RecentProduct recentProduct : recentProducts) {
            String productId = recentProduct.getProductId();

            Object product = productSearchService.searchProductById(productId);

            if (product != null) {
                products.add(product);
            }
        }

        return products;

    }

    // 최신 가격 정보 가져오기
//    public int getLastPrice(String index, String productId) throws IOException {
//        // "product_123" => "price_123"
//        String priceId = "price" + productId.split("_")[1];
//
//        // index, id로 상품 찾아서 시간 내림차순 정렬
//        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.boolQuery()
//                        .must(QueryBuilders.termQuery("_index", index))
//                        .must(QueryBuilders.termQuery("_id", priceId))
//                )
//                .withSort(Sort.by(Sort.Order.desc("price_history.timestamp")))
//                .build();// 정렬
//
//        // 검색결과
//        SearchHits<Map> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, Map.class);
//
//        if (!searchHits.isEmpty()) {
//            // 결과값 있으면 첫번째값의 price_history 가져오기
//            SearchHit<Map> hit = searchHits.getSearchHit(0);
//            Map<String, Object> source = hit.getContent();
//            List<Map<String, Object>> priceHistory = (List<Map<String, Object>>) source.getOrDefault("price_history", Collections.emptyList());
//
//            if (!priceHistory.isEmpty()){
//                // 첫 번째 원소의 가격 정보를 가져오기, string으로 저장되어 있어서 형 변환
//                return (int) priceHistory.get(0).get("price");
//            }
//        }
//
//        return -1; // 가격 정보가 없는 경우
//    }

}
