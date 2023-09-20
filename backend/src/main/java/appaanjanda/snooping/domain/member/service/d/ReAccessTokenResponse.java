package appaanjanda.snooping.domain.member.service.d;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReAccessTokenResponse {
	private String accessToken;
	private String refreshToken;
}
