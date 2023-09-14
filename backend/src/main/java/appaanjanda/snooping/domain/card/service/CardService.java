package appaanjanda.snooping.domain.card.service;

import java.util.List;

import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import appaanjanda.snooping.domain.card.entity.Card;
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
		List<Card> cards = cardRepository.findCardByMemberId(id);

		Boolean isdelete = new Boolean(false);

		for (Card card : cards) {
			if (card.getMyCard().equals(request.getMyCard())) {
				cardRepository.delete(card);
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

		Card card = Card.builder()
			.myCard(request.getMyCard())
			.member(member)
			.build();

		cardRepository.save(card);
	}
}