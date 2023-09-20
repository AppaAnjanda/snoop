package appaanjanda.snooping.domain.chat.chatting.dto;

import appaanjanda.snooping.domain.chat.chatting.ChatHistory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatSaveDto {
	private String content;

}