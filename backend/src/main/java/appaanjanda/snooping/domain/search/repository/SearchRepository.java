package appaanjanda.snooping.domain.search.repository;

import appaanjanda.snooping.domain.search.entity.SearchHistory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchRepository extends JpaRepository<SearchHistory, Long> {

    // 최근 검색어
    @Query("SELECT h FROM SearchHistory h WHERE h.member.id = :memberId ORDER BY h.createTime DESC")
    List<SearchHistory> findRecentKeywordsOrderByCreateTime(@Param("memberId") Long memberId);
}
