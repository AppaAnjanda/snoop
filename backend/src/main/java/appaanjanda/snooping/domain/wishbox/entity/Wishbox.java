package appaanjanda.snooping.domain.wishbox.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.global.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@NoArgsConstructor
public class Wishbox extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wishbox_id")
	private Long id;

	private String productName;

	private int price;

	private int alertPrice;

	private Boolean alertYn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wishboxList")
	private Member member;

	public Wishbox(Long id, String productName, int price, int alertPrice, Boolean alertYn, Member member) {
		this.id = id;
		this.productName = productName;
		this.price = price;
		this.alertPrice = alertPrice;
		this.alertYn = alertYn;
		this.member = member;
	}
}
