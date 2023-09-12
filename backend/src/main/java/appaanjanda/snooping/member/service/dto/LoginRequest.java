package appaanjanda.snooping.member.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

	private String email;
	private String password;

	@Builder
	public LoginRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
