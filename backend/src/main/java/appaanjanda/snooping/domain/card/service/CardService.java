package appaanjanda.snooping.domain.card.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	public void deleteMyCard(Long id, DeleteCardRequest request){
		Member member = memberRepository.findById(id).orElseThrow(()
				-> new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID));

		List<MyCard> myCardList = member.getMyCardList();

		Set<String> cardTypes = new HashSet<>(request.getMyCard());

		myCardList.stream()
				.filter(myCard -> cardTypes.contains(myCard.getCardType()))
				.forEach(myCard -> {
					log.info("mycardId={}", myCard.getId());
					cardRepository.delete(myCard.getId());
				});
	}

	public void updateMyCard(Long id, AddMyCardRequest request) {
		Member member = memberRepository.findById(id).orElseThrow(()
			-> new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID));

		request.getMyCard().stream()
				.map(card -> MyCard.builder()
						.cardType(card)
						.member(member)
						.build())
				.forEach(cardRepository::save);
	}
}