package appaanjanda.snooping.domain.hotProduct.service;

import appaanjanda.snooping.domain.hotProduct.entity.HotProduct;
import appaanjanda.snooping.domain.hotProduct.repository.HotProductRepository;
import appaanjanda.snooping.domain.product.service.ProductSearchService;
import appaanjanda.snooping.domain.search.dto.SearchContentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class HotProductService {

    private final HotProductRepository hotProductRepository;
    private final ProductSearchService productSearchService;

    // 조회 인기 상품
    public List<SearchContentDto> getHotProduct(Long memberId, String category) {

        List<HotProduct> hotProducts = hotProductRepository.sortAllByCount(category, Pageable.ofSize(10));

        List<SearchContentDto> resultProduct = new ArrayList<>();

        for (HotProduct hotProduct:hotProducts) {
            LocalDateTime createTime = hotProduct.getCreateTime();
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(createTime, now);

            // 일주일 지난 검색어 삭제
            if (duration.toDays() > 7) deleteHotProduct(hotProduct);
            // 검색어 추출
            SearchContentDto searchContentDto = productSearchService.searchProductById(hotProduct.getProductCode(), memberId);
            resultProduct.add(searchContentDto);
        }
        return resultProduct;
    }

    @Transactional
    // 조회인기상품 삭제
    public void deleteHotProduct(HotProduct hotProduct) {
        hotProductRepository.delete(hotProduct);
    }

    // 조회인기상품 생성 or 증가
    @Transactional
    public void updateHotProduct(String productCode) {

        Optional<HotProduct> hotProduct = hotProductRepository.findByProductCode(productCode);
        // 검색어 있으면 count +1
        if (hotProduct.isPresent()) {
            HotProduct existHotProduct = hotProduct.get();
            existHotProduct.setCount(existHotProduct.getCount() + 1);

        } else { // 없으면 새로 생성
            String majorCategory = productSearchService.getProduct(productCode).getMajorCategory();
            HotProduct newHotProduct = new HotProduct(productCode, majorCategory, 1);
            hotProductRepository.save(newHotProduct);
        }
    }
}
