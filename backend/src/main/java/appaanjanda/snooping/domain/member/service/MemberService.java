package appaanjanda.snooping.domain.member.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import appaanjanda.snooping.domain.card.entity.MyCard;
import appaanjanda.snooping.domain.card.repository.CardRepository;
import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.domain.member.entity.RefreshToken;
import appaanjanda.snooping.domain.member.entity.enumType.Role;
import appaanjanda.snooping.domain.member.repository.RefreshTokenRepository;
import appaanjanda.snooping.domain.member.service.dto.AccessTokenRequest;
import appaanjanda.snooping.domain.member.service.dto.AccessTokenResponse;
import appaanjanda.snooping.domain.member.service.dto.ChangeMyPasswordRequestDto;
import appaanjanda.snooping.domain.member.service.dto.LoginRequest;
import appaanjanda.snooping.domain.member.service.dto.LoginResponse;
import appaanjanda.snooping.domain.member.service.dto.UpdateUserRequestDto;
import appaanjanda.snooping.domain.member.service.dto.UpdateUserResponseDto;
import appaanjanda.snooping.domain.member.service.dto.UserResponse;
import appaanjanda.snooping.domain.member.service.dto.UserSaveRequestDto;
import appaanjanda.snooping.global.config.PasswordEncoder;
import appaanjanda.snooping.global.error.code.ErrorCode;
import appaanjanda.snooping.global.error.exception.BadRequestException;
// import appaanjanda.snooping.global.s3.S3Uploader;
import appaanjanda.snooping.jwt.JwtProvider;
import appaanjanda.snooping.domain.member.repository.MemberRepository;
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
	private final CardRepository cardRepository;
	// private final S3Uploader s3Uploader;
	private final PasswordEncoder passwordEncoder;


	// 유저 저장
	public String save(UserSaveRequestDto userSaveRequestDto) {

		List<MyCard> myCardList = new ArrayList<>();
		List<String> cardsList = userSaveRequestDto.getCardsList();



		Member member = Member.builder()
			.email(userSaveRequestDto.getEmail())
			.password(passwordEncoder.encrypt(userSaveRequestDto.getEmail(), userSaveRequestDto.getPassword()))
			.nickname(userSaveRequestDto.getNickname())
			.role(Role.USER)
			.build();

		memberRepository.saveAndFlush(member);

		for (String cardName : cardsList) {
			MyCard myCard = MyCard.builder()
				.cardType(cardName)
				.member(member)
				.build();

			myCardList.add(myCard);
		}

		member.setCardList(myCardList);

		return userSaveRequestDto.getEmail();
	}



	// 유저 정보 조회
	public UserResponse getUserInfo(Long id) {
		Member member = memberRepository.findById(id).orElseThrow(() ->
			new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID)
		);

		List<String> cardName = new ArrayList<>();

		List<MyCard> cardByMemberId = cardRepository.findCardByMemberId(member.getId());

		for (MyCard myCard : cardByMemberId) {
			cardName.add(myCard.getCardType());
		}

		return UserResponse.builder()
			.email(member.getEmail())
			.nickname(member.getNickname())
			.myCardList(cardName)
			.role(member.getRole())
			.build();
	}

	// 로그인
	public LoginResponse login(LoginRequest loginRequest) {
		Member member = memberRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() ->
			new BadRequestException(ErrorCode.INVALID_USER_DATA)
		);

		log.info("loginRequest.getPassword()={}", loginRequest.getPassword());
		log.info("passwordEncoder.encrypt(member.getEmail(), member.getPassword())={}", passwordEncoder.encrypt(member.getEmail(), member.getPassword()));


		if (!member.getPassword().equals(passwordEncoder.encrypt(loginRequest.getEmail(), loginRequest.getPassword()))) {
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

		return LoginResponse.builder()
			.accessToken(accessToken)
				.refreshToken(newRefreshToken.getRefreshToken())
			.build();
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
		Member member = memberRepository.findById(id).orElseThrow(() ->
				new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID)
			);
		if(passwordEncoder.encrypt(member.getEmail(),requestDto.getNowPassword()).equals(member.getPassword())  &&
			requestDto.getPasswordOne().equals(requestDto.getPasswordTwo())){
			member.setUserPassword(passwordEncoder.encrypt(member.getEmail(), member.getPassword()));
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

	// id로 멤버 찾기
	// TODO : exception 처리 수정
	public Member findMemberById(Long memberId){
		return memberRepository.findById(memberId)
				.orElseThrow(() -> new NotFoundException("멤버를 찾을 수 없습니다."));
	}
}
