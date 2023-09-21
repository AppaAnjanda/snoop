package appaanjanda.snooping.domain.chat.chatting;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ChatSaveDto {
	@JsonProperty("content")
	private String content;

}