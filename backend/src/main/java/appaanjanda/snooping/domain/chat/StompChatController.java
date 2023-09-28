package appaanjanda.snooping.domain.chat;

import appaanjanda.snooping.domain.chatreal.ChatService;
import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {

	// 특정 Broker 로 메세지를 전달
	private final SimpMessagingTemplate template;
	private final ChatService chatService;

	@MessageMapping("/chat/enter/{id}")
//	@SendTo("sub/{id}")
	public void enter(ChatMessageDto dto) {
		ChatRequest request = ChatRequest.builder()
				.roomidx(dto.getRoomidx())
				.email(dto.getEmail())
				.sender(dto.getWriter())
				.msg(dto.getWriter() + "님이 채팅방에 참여하였습니다.")
				.build();
		log.info("enter");
		// This will store the chat message in MongoDB
		chatService.recordHistory(request);

		template.convertAndSend("/sub/chat/room/" + dto.getRoomidx(), dto);

	}

	@MessageMapping(value = "/chat/{id}")
//	@SendTo("sub/{id}")
	public void message(ChatMessageDto dto) {

		ChatRequest request = ChatRequest.builder()
				.roomidx(dto.getRoomidx())
				.email(dto.getEmail())
				.sender(dto.getWriter())
				.msg(dto.getMessage())
				.build();
		log.info("send message");
		// This will store the chat message in MongoDB
		chatService.recordHistory(request);

		template.convertAndSend("/sub/chat/room/" + dto.getRoomidx(), dto);
		log.info("received message");
	}
}
