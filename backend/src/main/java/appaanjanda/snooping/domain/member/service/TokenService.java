package appaanjanda.snooping.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import appaanjanda.snooping.domain.member.entity.AccessToken;
import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.domain.member.repository.MemberRepository;
import appaanjanda.snooping.domain.member.repository.RefreshTokenRepository;
import appaanjanda.snooping.domain.member.service.d.ReAccessTokenResponse;
import appaanjanda.snooping.domain.member.service.dto.AccessTokenRequest;
import appaanjanda.snooping.domain.member.service.dto.AccessTokenResponse;
import appaanjanda.snooping.domain.member.service.dto.UserResponse;
import appaanjanda.snooping.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TokenService {

	private final JwtProvider jwtProvider;
	private final MemberRepository memberRepository;
	private final RefreshTokenRepository refreshTokenRepository;

	public ReAccessTokenResponse generateAccessToken (String refreshToken){

		Member byRefreshToken = memberRepository.findByRefreshToken(refreshToken);
		String accessToken = jwtProvider.createAccessToken(byRefreshToken);
		String newRefreshToken = jwtProvider.createRefreshToken(byRefreshToken);

		byRefreshToken.setRefreshToken(newRefreshToken);

		return ReAccessTokenResponse.builder()
			.accessToken(accessToken)
			.refreshToken(newRefreshToken)
			.build();

	}
}
