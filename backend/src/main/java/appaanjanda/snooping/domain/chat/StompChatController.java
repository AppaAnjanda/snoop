package appaanjanda.snooping.domain.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StompChatController {

	// 특정 Broker 로 메세지를 전달
	private final SimpMessagingTemplate template;

	// Client 가 Send 할 수 있는 경로
	// stompConfig 에서 설정한 applicationDestinationPrefixes 와 @MessageMapping 경로가 병합됨
	// /pub/chat/enter
	@MessageMapping(value = "/chat/enter")
	public void enter(ChatMessageDto dto) {
		dto.setMessage(dto.getWriter() + "님이 채팅방에 참여하였습니다.");
		template.convertAndSend("/sub/chat/room/" + dto.getRoomId(), dto);
	}


	@MessageMapping(value = "/chat/message")
	public void message(ChatMessageDto dto) {
		template.convertAndSend("/sub/chat/room/" + dto.getRoomId(), dto);
	}
}
