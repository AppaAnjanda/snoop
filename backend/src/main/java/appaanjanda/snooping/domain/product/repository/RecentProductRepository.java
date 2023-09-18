package appaanjanda.snooping.domain.product.repository;

import appaanjanda.snooping.domain.product.entity.RecentProduct;
import appaanjanda.snooping.domain.search.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecentProductRepository extends JpaRepository<RecentProduct, Long> {

    // 최근 본 상품
    @Query("SELECT r FROM RecentProduct r WHERE r.member.id = :memberId ORDER BY r.createTime DESC")
    List<RecentProduct> findRecentProductsOrderByCreateTime(Long memberId);
}
