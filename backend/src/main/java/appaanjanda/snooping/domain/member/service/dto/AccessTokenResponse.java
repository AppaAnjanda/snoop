package appaanjanda.snooping.domain.member.service.dto;


import appaanjanda.snooping.domain.member.entity.AccessToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenResponse {
	private String accessToken;
	private Long memberId;

	public AccessTokenResponse of(AccessToken accessToken){
		return AccessTokenResponse.builder()
			.accessToken(accessToken.getAccessToken())
			.memberId(accessToken.getMemberId())
			.build();
	}
}
