package appaanjanda.snooping.domain.card.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import appaanjanda.snooping.domain.card.entity.MyCard;

public interface CardRepository extends JpaRepository<MyCard, Long> {

	List<MyCard> findCardByMemberId(Long id);
}
