package appaanjanda.snooping.domain.member.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserResponseDto {

	private String email;

	private String nickname;

	@Builder
	public UpdateUserResponseDto(String email, String nickname) {
		this.email = email;
		this.nickname = nickname;
	}
}
