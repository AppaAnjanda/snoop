package appaanjanda.snooping.domain.card.service;

import java.util.ArrayList;
import java.util.List;

import appaanjanda.snooping.domain.card.service.dto.CardResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import appaanjanda.snooping.domain.card.entity.MyCard;
import appaanjanda.snooping.domain.card.repository.CardRepository;
import appaanjanda.snooping.domain.card.service.dto.UpdateMyCardRequest;
import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.domain.member.repository.MemberRepository;
import appaanjanda.snooping.global.error.code.ErrorCode;
import appaanjanda.snooping.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CardService {

	private final CardRepository cardRepository;
	private final MemberRepository memberRepository;

//	public void deleteMyCard(Long id, DeleteCardRequest request){
//		Member member = memberRepository.findById(id).orElseThrow(()
//				-> new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID));
//
//		List<MyCard> myCardList = member.getMyCardList();
//
//		Set<String> cardTypes = new HashSet<>(request.getMyCard());
//
//		myCardList.stream()
//				.filter(myCard -> cardTypes.contains(myCard.getCardType()))
//				.forEach(myCard -> {
//					log.info("mycardId={}", myCard.getId());
//					cardRepository.delete(myCard.getId());
//				});
//	}


	public void updateMyCard(Long id, UpdateMyCardRequest request) {
		Member member = memberRepository.findById(id).orElseThrow(()
			-> new BusinessException(ErrorCode.NOT_EXISTS_USER_ID));

		cardRepository.deleteAllByMember(member);

		List<MyCard> myCards = new ArrayList<>();

		request.getMyCard().stream()
				.map(card -> MyCard.builder()
						.cardType(card)
						.member(member)
						.build())
				.forEach(myCards::add);


		member.setCardList(myCards);

	}

	public CardResponse getMyCard(Long id){
		Member member = memberRepository.findById(id).orElseThrow(()
				-> new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID));

		List<String> cardName = new ArrayList<>();

		List<MyCard> cardByMemberId = cardRepository.findMyCardByMemberId(member.getId());

		for (MyCard myCard : cardByMemberId) {
			cardName.add(myCard.getCardType());
		}

		return CardResponse.builder()
				.myCardList(cardName)
				.build();

	}
}