package appaanjanda.snooping.domain.member.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccessTokenResponseFor {

	private String accessToken;

	@Builder
	public AccessTokenResponseFor(String accessToken) {
		this.accessToken = accessToken;
	}
}
