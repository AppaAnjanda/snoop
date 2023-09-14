package appaanjanda.snooping.domain.card.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import appaanjanda.snooping.domain.card.entity.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

	List<Card> findCardByMemberId(Long id);
}
