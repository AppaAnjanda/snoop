package appaanjanda.snooping.member.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

	private String email;

	private String password;

	private String nickname;

}
