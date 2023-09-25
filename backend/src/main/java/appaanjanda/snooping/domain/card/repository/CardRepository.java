package appaanjanda.snooping.domain.card.repository;

import java.util.List;

import appaanjanda.snooping.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import appaanjanda.snooping.domain.card.entity.MyCard;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<MyCard, Long> {


	@Modifying
	@Query("delete from MyCard m where m.id = :id")
	void delete(@Param("id") Long id);

	void deleteAllByMember(Member member);

	List<MyCard> findMyCardByMemberId(Long id);
	void deleteMyCardByCardType(String cardType);
}
