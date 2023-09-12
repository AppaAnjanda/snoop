package appaanjanda.snooping.member.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import appaanjanda.snooping.jwt.JwtProvider;
import appaanjanda.snooping.member.entity.RefreshToken;
import appaanjanda.snooping.member.entity.Member;
import appaanjanda.snooping.member.repository.RefreshTokenRepository;
import appaanjanda.snooping.member.repository.MemberRepository;
import appaanjanda.snooping.member.service.dto.AccessTokenRequest;
import appaanjanda.snooping.member.service.dto.AccessTokenResponse;
import appaanjanda.snooping.member.service.dto.RefreshTokenRequest;
import appaanjanda.snooping.member.service.dto.RefreshTokenResponse;
import io.jsonwebtoken.Jwts;
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

	public RefreshTokenResponse generateRefreshToken(RefreshTokenRequest request) {
		Member member = memberRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
			.orElseThrow();

		String createRefreshToken = jwtProvider.createRefreshToken(member);

		RefreshToken newRefreshToken = RefreshToken.builder()
			.refreshToken(createRefreshToken)
			.memberId(member.getId())
			.build();

		refreshTokenRepository.save(newRefreshToken);

		return new RefreshTokenResponse().of(newRefreshToken);
	}

	public AccessTokenResponse generateAccessToken(AccessTokenRequest request) {
		RefreshToken refreshToken = refreshTokenRepository.findById(request.getRefreshToken())
			.orElseThrow();
		Long memberId = refreshToken.getMemberId();
		Member member = memberRepository.findById(memberId).orElseThrow();

		String accessToken = jwtProvider.createAccessToken(member);

		return new AccessTokenResponse(accessToken);
	}

	// public Long extractMemberId(final String accessToken) {
	// 	try {
	// 		String memberId = Jwts.parserBuilder()
	// 			.setSigningKey(secretKey)
	// 			.build()
	// 			.parseClaimsJws(accessToken)
	// 			.getBody()
	// 			.getSubject();
	// 		return Long.parseLong(memberId);
	// 	} catch (final JwtException e) {
	// 		throw new InvalidAccessTokenException();
	// 	}
	// }
}
