package appaanjanda.snooping.domain.chat.chatting.dto;

import appaanjanda.snooping.domain.chat.chatting.ChatHistory;
import lombok.Data;

@Data
public class ChatSaveDto {
	private String content;

	public ChatHistory toEntity() {
		ChatHistory chatHistory = ChatHistory.builder()
			.msg(content)
			.build();
		return chatHistory;
	}
}