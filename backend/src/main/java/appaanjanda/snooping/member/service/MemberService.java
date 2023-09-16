package appaanjanda.snooping.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import appaanjanda.snooping.jwt.JwtProvider;
import appaanjanda.snooping.member.entity.Member;
import appaanjanda.snooping.member.entity.RefreshToken;
import appaanjanda.snooping.member.entity.enumType.Role;
import appaanjanda.snooping.member.repository.MemberRepository;
import appaanjanda.snooping.member.repository.RefreshTokenRepository;
import appaanjanda.snooping.member.service.dto.AccessTokenRequest;
import appaanjanda.snooping.member.service.dto.AccessTokenResponse;
import appaanjanda.snooping.member.service.dto.LoginRequest;
import appaanjanda.snooping.member.service.dto.RefreshTokenRequest;
import appaanjanda.snooping.member.service.dto.UpdateUserRequestDto;
import appaanjanda.snooping.member.service.dto.UpdateUserResponseDto;
import appaanjanda.snooping.member.service.dto.UserResponse;
import appaanjanda.snooping.member.service.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.webjars.NotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;
	private final TokenService tokenService;
	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtProvider jwtProvider;

	// 유저 저장
	public String save(UserSaveRequestDto userSaveRequestDto) {

		Member member = Member.builder()
			.email(userSaveRequestDto.getEmail())
			.password(userSaveRequestDto.getPassword())
			.nickname(userSaveRequestDto.getNickname())
			.role(Role.USER)
			.build();

		memberRepository.save(member);
		return userSaveRequestDto.getEmail();
	}


	// 유저 정보 조회
	public UserResponse getUserInfo(Long id) {
		Member member = memberRepository.findById(id).orElseThrow();

		return UserResponse.builder()
			.email(member.getEmail())
			.nickname(member.getNickname())
			.role(member.getRole())
			.build();
	}

	// 로그인
	public String login(LoginRequest loginRequest) {
		Member member = memberRepository.findByEmail(loginRequest.getEmail()).orElseThrow();

		if (!loginRequest.getPassword().equals(member.getPassword())) {
			throw new RuntimeException();
		}

		// 로그인 시 엑세스 토큰, 리프레시 토큰 발급
		String accessToken = jwtProvider.createAccessToken(member);
		String refreshToken = jwtProvider.createRefreshToken(member);

		RefreshToken newRefreshToken = RefreshToken.builder()
			.refreshToken(refreshToken)
			.memberId(member.getId())
			.build();

		// 레디스에 리프레시 토큰 저장
		refreshTokenRepository.save(newRefreshToken);

		return "Bearer " + accessToken + " \n" + "Bearer " + refreshToken;
	}

	// 닉네임 변경
	public UpdateUserResponseDto updateNickname(UpdateUserRequestDto updateUserRequestDto,Long id) {
		Member member = memberRepository.findById(id).orElseThrow();
		member.setNickname(updateUserRequestDto.getNickName());

		return UpdateUserResponseDto.builder()
			.email(member.getEmail())
			.nickname(member.getNickname())
			.build();
	}

	// 유저 삭제
	public String deleteUser(Long id){
		Member member = memberRepository.findById(id).orElseThrow();
		memberRepository.delete(member);

		return "삭제";
	}


	// 유저 accessToken 재발급
	public String getAccessToken(AccessTokenRequest request){
		log.info("refreshToken={}", request.getRefreshToken());

		AccessTokenResponse accessTokenResponse = tokenService.generateAccessToken(request);

		return accessTokenResponse.getAccessToken();
	}

	// 비밀번호 변경
	// public String


	// 프로필 이미지 변경

	// 이메일로 비밀 번호 찾기 -> sendeEmailServicie

	// id로 멤버 찾기
	// TODO : exception 처리 수정
	public Member findMemberById(Long memberId){
		return memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException("멤버를 찾을 수 없습니다."));
	}
}
