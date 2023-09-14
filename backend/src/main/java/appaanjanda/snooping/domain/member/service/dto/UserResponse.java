package appaanjanda.snooping.domain.member.service.dto;

import appaanjanda.snooping.domain.member.entity.enumType.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserResponse {

	private String email;

	private String nickname;

	private Role role;

	@Builder
	public UserResponse(String email, String nickname, Role role) {
		this.email = email;
		this.nickname = nickname;
		this.role = role;
	}
}
