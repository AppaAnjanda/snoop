package appaanjanda.snooping.domain.member.service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MailDto {
	private String address;
	private String title;
	private String message;
}
