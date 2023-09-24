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
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
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

	private int alertPrice;

	private Boolean alertYn;

	/**
	Elasticsearch에 연결되는 상품 code
	 */
	private String productCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wishboxList")
	@JsonIgnore
	private Member member;

	@Builder
	public Wishbox(Long id, int alertPrice, Boolean alertYn, String productCode, Member member) {
		this.id = id;
		this.alertPrice = alertPrice;
		this.alertYn = alertYn;
		this.productCode = productCode;
		this.member = member;
	}
}
