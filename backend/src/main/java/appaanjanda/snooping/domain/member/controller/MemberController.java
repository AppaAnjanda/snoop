package appaanjanda.snooping.domain.member.controller;

import appaanjanda.snooping.domain.member.service.MemberService;
import appaanjanda.snooping.domain.member.service.dto.AccessTokenRequest;
import appaanjanda.snooping.domain.member.service.dto.ChangeMyPasswordRequestDto;
import appaanjanda.snooping.domain.member.service.dto.LoginRequest;
import appaanjanda.snooping.domain.member.service.dto.LoginResponse;
import appaanjanda.snooping.domain.member.service.dto.UpdateUserRequestDto;
import appaanjanda.snooping.domain.member.service.dto.UpdateUserResponseDto;
import appaanjanda.snooping.domain.member.service.dto.UserResponse;
import appaanjanda.snooping.domain.member.service.dto.UserSaveRequestDto;
import appaanjanda.snooping.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

	private final MemberService memberService;

	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "회원가입 성공",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = UserResponse.class))),
		@ApiResponse(responseCode = "400", description = "요청 오류 "),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류")
	})
	@Operation(summary = "회원가입", description = "회원가입 시 이메일, 패스워드, 내가 가지고 있는 cardlist를 request로 받는다", tags = { "Member Controller" })
	@PostMapping("/save")
	public Response<?> save(@RequestBody UserSaveRequestDto userSaveRequestDto){
		return new Response<>(HttpStatus.CREATED, "회원가입 성공" ,memberService.save(userSaveRequestDto));
	}

	@ApiResponses(value = {
		@ApiResponse(responseCode = "226", description = "로그인 성공",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = LoginResponse.class))),
		@ApiResponse(responseCode = "400", description = "요청 오류 "),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류")
	})
	@Operation(summary = "로그인", description = "로그인 시 토큰 발급", tags = { "Member Controller" })
	@PostMapping("/login")
	public Response<?> login (@RequestBody LoginRequest loginRequest) {
		return new Response<>(HttpStatus.IM_USED, "로그인 성공" ,memberService.login(loginRequest));
	}


	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "회원가입 성공",
			content = @Content(mediaType = "application/json",
			schema = @Schema(implementation = UserResponse.class))),
		@ApiResponse(responseCode = "400", description = "요청 오류 "),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류")
	})
	@SecurityRequirement(name = "Bearer Authentication")
	@Operation(summary = "멤버 정보", description = "멤버 정보 불러오기", tags = { "Member Controller" })
	@GetMapping("/info")
	public Response<?> getUser (@MemberInfo MembersInfo membersInfo) {
		return new Response<>(HttpStatus.NO_CONTENT, "내 정보 불러오기",memberService.getUserInfo(membersInfo.getId()));
	}


	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "닉네임 변경 성공",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = UpdateUserResponseDto.class))),
		@ApiResponse(responseCode = "400", description = "요청 오류 "),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류")
	})
	@SecurityRequirement(name = "Bearer Authentication")
	@Operation(summary = "닉네임 변경", description = "서비스 내 나의 닉네임 변경", tags = { "Member Controller" })
	@PutMapping("/change")
	public Response<?> updateNickname (@MemberInfo MembersInfo membersInfo, @RequestBody UpdateUserRequestDto updateUserRequestDto){
		return new Response<>(HttpStatus.OK, "내 닉네임 변경" ,memberService.updateNickname(updateUserRequestDto, membersInfo.getId()));
	}

	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "멤버 삭제 성공"),
		@ApiResponse(responseCode = "400", description = "요청 오류 "),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류")
	})
	@SecurityRequirement(name = "Bearer Authentication")
	@Operation(summary = "멤버 삭제", description = "멤버 정보 삭제", tags = { "Member Controller" })
	@DeleteMapping("/delete")
	public void delete (@MemberInfo MembersInfo membersInfo){
		memberService.deleteUser(membersInfo.getId());
	}


	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "토큰 재발급 성공",
			content = @Content(mediaType = "application/json",
				schema = @Schema(implementation = String.class))),
		@ApiResponse(responseCode = "400", description = "요청 오류 "),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류")
	})
	@Operation(summary = "토큰 재발급", description = "refreshToken이 있을 때 AccessToken 재발급한다", tags = { "Member Controller" })
	@PostMapping("/token")
	public Response<?> getAccessToken (@RequestBody AccessTokenRequest request){
		return new Response<>( HttpStatus.CREATED, "토큰 재발급", memberService.getAccessToken(request));
	}


	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "비밀번호 변경 성공"),
		@ApiResponse(responseCode = "400", description = "요청 오류"),
		@ApiResponse(responseCode = "500", description = "서버 내부 오류")
	})
	@Operation(summary = "비밀번호 변경", description = "현재 비밀번호를 입력후 1, 2차 확인 후 변경할 비밀번호로 바꾼다 ", tags = { "Member Controller" })
	@PutMapping("/changePassword")
	public void changeMyPassword (@MemberInfo MembersInfo membersInfo,@RequestBody ChangeMyPasswordRequestDto requestDto){
		memberService.changeMyPassword(membersInfo.getId(), requestDto);

	}

}
