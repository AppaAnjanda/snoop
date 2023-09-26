package appaanjanda.snooping.domain.wishbox.repository;

import appaanjanda.snooping.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import appaanjanda.snooping.domain.wishbox.entity.Wishbox;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface WishboxRepository extends JpaRepository<Wishbox, Long> {

    // 회원이 찜한 상품 코드 목록
    @Query("SELECT w.productCode FROM Wishbox w WHERE w.member.id = :memberId")
    Set<String> findProductById(Long memberId);

    // 회원 찜 목록중 상품 코드 일치 목록
    @Query("SELECT w FROM Wishbox w WHERE w.productCode = :productCode and w.member.id = :memberId")
    Optional<Wishbox> findByProductCodeAndMemberId(String productCode, Long memberId);

    List<Wishbox> findByMember(Member member);
}
