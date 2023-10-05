package appaanjanda.snooping.domain.card.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import appaanjanda.snooping.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MyCard {

	@Id
	@Column(name = "card_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String cardType;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@Builder
	public MyCard(Long id, String cardType, Member member) {
		this.id = id;
		this.cardType = cardType;
		this.member = member;
	}
}
