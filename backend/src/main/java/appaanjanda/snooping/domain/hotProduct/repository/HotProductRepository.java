package appaanjanda.snooping.domain.hotProduct.repository;

import appaanjanda.snooping.domain.hotKeyword.entity.HotKeyword;
import appaanjanda.snooping.domain.hotProduct.entity.HotProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotProductRepository extends JpaRepository<HotProduct, Long> {

    // 카테고리 일치상품 중 인기 상품 10개
    @Query("SELECT h FROM HotProduct h WHERE h.category = :category ORDER BY h.count DESC")
    List<HotProduct> sortAllByCount(String category, Pageable pageable);

    Optional<HotProduct> findByProductCode(String productCode);
}
