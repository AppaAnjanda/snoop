package appaanjanda.snooping.domain.card.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import appaanjanda.snooping.domain.card.entity.MyCard;
import appaanjanda.snooping.domain.card.repository.CardRepository;
import appaanjanda.snooping.domain.card.service.dto.AddMyCardRequest;
import appaanjanda.snooping.domain.card.service.dto.DeleteCardRequest;
import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.domain.member.repository.MemberRepository;
import appaanjanda.snooping.global.error.code.ErrorCode;
import appaanjanda.snooping.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CardService {

	private final CardRepository cardRepository;
	private final MemberRepository memberRepository;

	public void deleteCard(Long id, DeleteCardRequest request) {
		List<MyCard> myCards = cardRepository.findCardByMemberId(id);

		boolean isdelete = false;
		log.info("request.getMyCard() = {}", request.getMyCard());
		for (MyCard myCard : myCards) {
			log.info("myCard = {}", myCard.getCardType());
			if (myCard.getCardType().equals(request.getMyCard())) {
				cardRepository.delete(myCard);
				isdelete = true;
			}
		}
		if (!isdelete) {
			throw new BadRequestException(ErrorCode.NOT_EXISTS_CARD_NAME);
		}
	}

	public void updateMyCard(Long id, AddMyCardRequest request) {
		Member member = memberRepository.findById(id).orElseThrow(()
			-> new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID));

		MyCard myCard = MyCard.builder()
			.cardType(request.getMyCard())
			.member(member)
			.build();

		cardRepository.save(myCard);
	}
}