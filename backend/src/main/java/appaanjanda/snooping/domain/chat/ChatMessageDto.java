package appaanjanda.snooping.domain.chat;

import lombok.Data;

@Data
public class ChatMessageDto {
	private Long roomidx;
	private String email;
	private String message;
}
