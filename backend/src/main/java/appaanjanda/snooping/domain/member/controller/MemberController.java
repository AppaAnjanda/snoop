package appaanjanda.snooping.domain.member.controller;

import appaanjanda.snooping.domain.member.service.MemberService;
import appaanjanda.snooping.domain.member.service.dto.AccessTokenRequest;
import appaanjanda.snooping.domain.member.service.dto.ChangeMyPasswordRequestDto;
import appaanjanda.snooping.domain.member.service.dto.LoginRequest;
import appaanjanda.snooping.domain.member.service.dto.UpdateUserRequestDto;
import appaanjanda.snooping.domain.member.service.dto.UpdateUserResponseDto;
import appaanjanda.snooping.domain.member.service.dto.UserResponse;
import appaanjanda.snooping.domain.member.service.dto.UserSaveRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

	private final MemberService memberService;

	@Operation(summary = "회원가입", description = "회원가입 시 이메일, 패스워드, 내가 가지고 있는 cardlist를 request로 받는다", tags = { "Member Controller" })
	@PostMapping("/save")
	public String save(@RequestBody UserSaveRequestDto userSaveRequestDto){
		return memberService.save(userSaveRequestDto);
	}

	@Operation(summary = "로그인", description = "로그인 시 토큰 발급", tags = { "Member Controller" })
	@PostMapping("/login")
	public String login (@RequestBody LoginRequest loginRequest) throws Exception {
		return memberService.login(loginRequest);
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@Operation(summary = "멤버 정보", description = "멤버 정보 불러오기", tags = { "Member Controller" })
	@GetMapping("/info")
	public UserResponse getUser (@MemberInfo MembersInfo membersInfo) throws Exception {
		return memberService.getUserInfo(membersInfo.getId());
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@Operation(summary = "닉네임 변경", description = "서비스 내 나의 닉네임 변경", tags = { "Member Controller" })
	@PutMapping("/change")
	public UpdateUserResponseDto updateNickname (@MemberInfo MembersInfo membersInfo, @RequestBody UpdateUserRequestDto updateUserRequestDto){
		return memberService.updateNickname(updateUserRequestDto, membersInfo.getId());
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@Operation(summary = "멤버 삭제", description = "멤버 정보 삭제", tags = { "Member Controller" })
	@DeleteMapping("/delete")
	public void delete (@MemberInfo MembersInfo membersInfo){
		memberService.deleteUser(membersInfo.getId());
	}

	@Operation(summary = "토큰 재발급", description = "refreshToken이 있을 때 AccessToken 재발급한다", tags = { "Member Controller" })
	@PostMapping("/token")
	public String getAccessToken (@RequestBody AccessTokenRequest request){
		return memberService.getAccessToken(request);
	}

	@Operation(summary = "비밀번호 변경", description = "현재 비밀번호를 입력후 1, 2차 확인 후 변경할 비밀번호로 바꾼다 ", tags = { "Member Controller" })
	@PostMapping("/token")
	public void changeMyPassword (@MemberInfo MembersInfo membersInfo, ChangeMyPasswordRequestDto requestDto){
		memberService.changeMyPassword(membersInfo.getId(), requestDto);

	}

}
