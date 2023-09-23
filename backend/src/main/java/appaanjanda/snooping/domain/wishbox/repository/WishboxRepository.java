package appaanjanda.snooping.domain.wishbox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import appaanjanda.snooping.domain.wishbox.entity.Wishbox;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface WishboxRepository extends JpaRepository<Wishbox, Long> {

    @Query("SELECT w.productId FROM Wishbox w WHERE w.member.id = :memberId")
    Set<String> findProductById(Long memberId);
}
