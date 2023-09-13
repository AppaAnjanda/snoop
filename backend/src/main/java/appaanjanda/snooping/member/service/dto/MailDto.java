package appaanjanda.snooping.member.service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MailDto {
	private String address;
	private String title;
	private String message;
}
