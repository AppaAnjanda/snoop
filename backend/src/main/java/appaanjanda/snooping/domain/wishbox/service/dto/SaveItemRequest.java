package appaanjanda.snooping.domain.wishbox.service.dto;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import appaanjanda.snooping.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class SaveItemRequest {

	private String productName;

	private int price;

	private int alertPrice;

	private Boolean alertYn;

	private Member member;


}
