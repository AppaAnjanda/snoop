package appaanjanda.snooping.domain.card.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import appaanjanda.snooping.domain.card.service.CardService;
import appaanjanda.snooping.domain.card.service.dto.AddMyCardRequest;
import appaanjanda.snooping.domain.card.service.dto.DeleteCardRequest;
import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/card")
@RestController
@RequiredArgsConstructor
public class CardController {

	private final CardService cardService;

	@SecurityRequirement(name = "Bearer Authentication")
	@Operation(summary = "카드 추가", description = "유저 정보를 jwt로 확인하고 request로 넘겨준 cardname을 추가한다.", tags = { "Card Controller" })
	@DeleteMapping("/delete")
	public void deleteMyCard(@MemberInfo MembersInfo membersInfo, @RequestBody DeleteCardRequest request){
		cardService.deleteMyCard(membersInfo.getId(), request);
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@Operation(summary = "카드 추가", description = "유저 정보를 jwt로 확인하고 request로 넘겨준 cardname을 추가한다.", tags = { "Card Controller" })
	@PutMapping("/addCard")
	public void updateMyCard(@MemberInfo MembersInfo membersInfo, @RequestBody AddMyCardRequest request){
		cardService.updateMyCard(membersInfo.getId(), request);
	}
}
