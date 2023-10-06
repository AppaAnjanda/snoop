package appaanjanda.snooping.domain.member.service.dto;

import appaanjanda.snooping.domain.member.entity.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponse {
	private String refreshToken;
	private Long memberId;

	public RefreshTokenResponse of(RefreshToken refreshToken){
		return RefreshTokenResponse.builder()
			.refreshToken(refreshToken.getRefreshToken())
			.memberId(refreshToken.getMemberId())
			.build();
	}
}
