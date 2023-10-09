package appaanjanda.snooping.domain.recentProduct.service;

import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.domain.member.service.MemberService;
import appaanjanda.snooping.domain.product.service.ProductSearchService;
import appaanjanda.snooping.domain.recentProduct.entity.RecentProduct;
import appaanjanda.snooping.domain.recentProduct.repository.RecentProductRepository;
import appaanjanda.snooping.domain.search.dto.SearchContentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RecentProductService {

    private final ProductSearchService productSearchService;
    private final MemberService memberService;
    private final RecentProductRepository recentProductRepository;
    private final EntityManager em;


    // 최근 본 상품 추가
    @Transactional
    public void updateRecentProduct(Long memberId, String productCode) {

        Member member = memberService.findMemberById(memberId);
        // 최근 본 상품
        List<RecentProduct> recentProducts = recentProductRepository.findRecentProductsOrderByUpdateTime(memberId);

        log.info("최근 본 상품");
        // 최근 본 상품에 현재 상품이 포함되어 있는지 확인, 없으면 null
        RecentProduct existProduct = findExistingProduct(recentProducts, productCode);
        if(existProduct != null) {
            log.info("중복");
            existProduct.updateTime();
        } else {
            // 최근 본 상품 10개 이상이면 마지막꺼 지우기
            log.info("10개");
            checkAndRemoveOldestIfExceedsLimit(recentProducts);
            log.info("생성");
            saveNewRecentProduct(member, productCode);
        }

    }
    // 이미 봤던 상품인지 확인
    private RecentProduct findExistingProduct(List<RecentProduct> recentProducts, String productCode) {
        log.info("봤는지 확인 {}", productCode);
        return recentProducts.stream()
                .filter(search -> search.getProductCode().equals(productCode))
                .findFirst()
                .orElse(null);
    }

    // 최근 처음 조회하는 상품
    private void saveNewRecentProduct(Member member, String productCode) {
        log.info("처음조회");
        RecentProduct newWatchProduct = new RecentProduct();
        newWatchProduct.setMember(member);
        newWatchProduct.setProductCode(productCode);
        recentProductRepository.save(newWatchProduct);
    }

    private void checkAndRemoveOldestIfExceedsLimit(List<RecentProduct> recentProducts) {
        if (recentProducts.size() >= 10) {
            log.info("10개22");
            RecentProduct oldestProduct = recentProducts.get(recentProducts.size()-1);
            log.info(String.valueOf(oldestProduct.getId()));
            recentProductRepository.deleteByProductId(oldestProduct.getId());
        }
    }

    // 최근 본 상품 리스트
    public List<Object> getRecentProduct(Long memberId) {
        // 현재 사용자의 최근 본 상품들
        List<RecentProduct> recentProducts = recentProductRepository.findRecentProductsOrderByUpdateTime(memberId);

        // 최근 본 상품의 상세 정보 리스트
        List<Object> products = new ArrayList<>();

        for (RecentProduct recentProduct : recentProducts) {
            // 상품 코드로 상세정보 가져와서 추가
            String productCode = recentProduct.getProductCode();
            SearchContentDto product;
            // 조회 안되면 생략
            try {
                product = productSearchService.searchProductById(productCode, memberId);
            } catch (Exception e) {
                continue;
            }

            products.add(product);
        }
        return products;
    }

}
