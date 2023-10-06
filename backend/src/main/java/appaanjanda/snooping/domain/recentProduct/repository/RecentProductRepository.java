package appaanjanda.snooping.domain.recentProduct.repository;

import appaanjanda.snooping.domain.recentProduct.entity.RecentProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecentProductRepository extends JpaRepository<RecentProduct, Long> {

    // 최근 본 상품
    @Query("SELECT DISTINCT r FROM RecentProduct r WHERE r.member.id = :memberId ORDER BY r.updateTime DESC")
    List<RecentProduct> findRecentProductsOrderByUpdateTime(Long memberId);

    @Query("DELETE FROM RecentProduct r WHERE r.id = :id")
    @Modifying
    void deleteByProductId(Long id);
}
