package appaanjanda.snooping.domain.hotKeyword.repository;

import appaanjanda.snooping.domain.hotKeyword.entity.HotKeyword;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotKeywordRepository extends JpaRepository<HotKeyword, Long> {

    @Query("SELECT h FROM HotKeyword h ORDER BY h.count DESC")
    List<HotKeyword> sortAllByCount(Pageable pageable);

    Optional<HotKeyword> findByKeyword(String keyword);

}
