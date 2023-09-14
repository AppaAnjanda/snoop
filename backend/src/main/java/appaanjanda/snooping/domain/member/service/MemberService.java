package appaanjanda.snooping.domain.member.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import appaanjanda.snooping.domain.card.entity.Card;
import appaanjanda.snooping.domain.card.repository.CardRepository;
import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.domain.member.entity.RefreshToken;
import appaanjanda.snooping.domain.member.entity.enumType.Role;
import appaanjanda.snooping.domain.member.repository.RefreshTokenRepository;
import appaanjanda.snooping.domain.member.service.dto.AccessTokenRequest;
import appaanjanda.snooping.domain.member.service.dto.AccessTokenResponse;
import appaanjanda.snooping.domain.member.service.dto.ChangeMyPasswordRequestDto;
import appaanjanda.snooping.domain.member.service.dto.SendEmailRequestDto;
import appaanjanda.snooping.domain.member.service.dto.LoginRequest;
import appaanjanda.snooping.domain.member.service.dto.UpdateProfilePictureDto;
import appaanjanda.snooping.domain.member.service.dto.UpdateUserRequestDto;
import appaanjanda.snooping.domain.member.service.dto.UpdateUserResponseDto;
import appaanjanda.snooping.domain.member.service.dto.UserResponse;
import appaanjanda.snooping.domain.member.service.dto.UserSaveRequestDto;
import appaanjanda.snooping.global.error.code.ErrorCode;
import appaanjanda.snooping.global.error.exception.BadRequestException;
// import appaanjanda.snooping.global.s3.S3Uploader;
import appaanjanda.snooping.jwt.JwtProvider;
import appaanjanda.snooping.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;
	private final TokenService tokenService;
	private final RefreshTokenRepository refreshTokenRepository;
	private final JwtProvider jwtProvider;
	private final CardRepository cardRepository;
	// private final S3Uploader s3Uploader;


	// 유저 저장
	public String save(UserSaveRequestDto userSaveRequestDto) {

		List<Card> cardList = new ArrayList<>();
		List<String> cardsList = userSaveRequestDto.getCardsList();

		for (String cardName : cardsList) {
			Card card = Card.builder()
				.myCard(cardName)
				.build();

			cardList.add(card);
		}

		Member member = Member.builder()
			.email(userSaveRequestDto.getEmail())
			.password(userSaveRequestDto.getPassword())
			.nickname(userSaveRequestDto.getNickname())
			.role(Role.USER)
			.cardList(cardList)
			.build();

		memberRepository.save(member);
		return userSaveRequestDto.getEmail();
	}



	// 유저 정보 조회
	public UserResponse getUserInfo(Long id) {
		Member member = memberRepository.findById(id).orElseThrow(() ->
			new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID)
		);

		return UserResponse.builder()
			.email(member.getEmail())
			.nickname(member.getNickname())
			.role(member.getRole())
			.build();
	}

	// 로그인
	public String login(LoginRequest loginRequest) {
		Member member = memberRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() ->
			new BadRequestException(ErrorCode.INVALID_USER_DATA)
		);

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
		Member member = memberRepository.findById(id).orElseThrow(() ->
			new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID)
		);
		member.setNickname(updateUserRequestDto.getNickName());

		return UpdateUserResponseDto.builder()
			.email(member.getEmail())
			.nickname(member.getNickname())
			.build();
	}

	// 유저 삭제
	public void deleteUser(Long id){
		Member member = memberRepository.findById(id).orElseThrow(() ->
			new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID)
		);
		memberRepository.delete(member);
	}


	// 유저 accessToken 재발급
	public String getAccessToken(AccessTokenRequest request){
		log.info("refreshToken={}", request.getRefreshToken());

		AccessTokenResponse accessTokenResponse = tokenService.generateAccessToken(request);

		return accessTokenResponse.getAccessToken();
	}

	// 비밀번호 변경
	public void changeMyPassword(Long id, ChangeMyPasswordRequestDto requestDto){
		Member member = memberRepository.findById(id).orElseThrow();
		if(member.getPassword().equals(requestDto.getPasswordOne()) &&
			requestDto.getPasswordOne().equals(requestDto.getPasswordTwo())){
			member.setUserPassword(member.getPassword());
		}
	}

	// 프로필 이미지 변경
	// public UpdateProfilePictureDto updateProfilePicture(MultipartFile multipartFile, Long id) throws IOException {
	//
	// 	Member member = memberRepository.findById(id).orElseThrow(
	// 		() -> new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID)
	// 	);
	//
	// 	String upload = s3Uploader.uploadFiles(multipartFile, "Profile");
	//
	// 	member.setProfileImageUrl(upload);
	//
	// 	// userRepository.save(user);
	// 	/*파일 저장*/
	//
	// 	return new UpdateProfilePictureDto(upload);
	// }

	// 이메일로 비밀 번호 찾기 -> sendeEmailServicie
}
