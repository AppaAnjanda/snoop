package appaanjanda.snooping.domain.chat;

import lombok.Data;

@Data
public class ChatMessageDto {
	private Long roomidx;
	private String writer;
	private String message;
}
