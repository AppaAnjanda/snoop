package appaanjanda.snooping.domain.card.controller;

import appaanjanda.snooping.domain.card.service.dto.CardResponse;
import org.springframework.web.bind.annotation.*;

import appaanjanda.snooping.domain.card.service.CardService;
import appaanjanda.snooping.domain.card.service.dto.UpdateMyCardRequest;
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

//	@SecurityRequirement(name = "Bearer Authentication")
//	@Operation(summary = "카드 삭제", description = "내 jwtToken으로 갖고 있는 카드를 삭제할 수 있다.", tags = { "Card Controller" })
//	@DeleteMapping("/delete")
//	public void deleteMyCard(@MemberInfo MembersInfo membersInfo, @RequestBody DeleteCardRequest request){
//		cardService.deleteMyCard(membersInfo.getId(), request);
//	}

	@SecurityRequirement(name = "Bearer Authentication")
	@Operation(summary = "카드 추가", description = "유저 정보를 jwt로 확인하고 request로 넘겨준 cardname을 추가한다.", tags = { "Card Controller" })
	@PutMapping("/addCard")
	public void updateMyCard(@MemberInfo MembersInfo membersInfo, @RequestBody UpdateMyCardRequest request){
		cardService.updateMyCard(membersInfo.getId(), request);
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@Operation(summary = "내가 갖고 있는 카드 조회", description = "내 jwtToken으로 갖고 있는 카드를 조회할 수 있다", tags = { "Card Controller" })
	@GetMapping("/myCard")
	public CardResponse getMyCard(@MemberInfo MembersInfo membersInfo){
		return cardService.getMyCard(membersInfo.getId());
	}
}
