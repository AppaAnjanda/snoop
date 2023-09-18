package appaanjanda.snooping.domain.member.service.dto;

import lombok.Getter;

@Getter
public class ChangeMyPasswordRequestDto {

	private String nowPassword;
	private String passwordOne;
	private String passwordTwo;
}
