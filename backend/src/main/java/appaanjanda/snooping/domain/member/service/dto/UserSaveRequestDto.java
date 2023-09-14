package appaanjanda.snooping.domain.member.service.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

	private String email;

	private String password;

	private String nickname;

	private List<String> cardsList = new ArrayList<>();

}
