package appaanjanda.snooping.domain.chat;

import appaanjanda.snooping.domain.chatreal.ChatService;
import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.domain.member.repository.MemberRepository;
import appaanjanda.snooping.global.error.code.ErrorCode;
import appaanjanda.snooping.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Locale;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {

	// 특정 Broker 로 메세지를 전달
	private final SimpMessagingTemplate template;
	private final ChatService chatService;
	private final MemberRepository memberRepository;

	@MessageMapping("/chat/enter/{id}")
	@SendTo("/sub/chat/room/{id}")
	public ChatRequest enter(ChatMessageDto dto) {

		Member member = memberRepository.findMemberByEmail(dto.getEmail()).orElseThrow(() ->
				new BusinessException(ErrorCode.NOT_EXISTS_USER_ID));

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 a hh:mm", Locale.KOREAN);
		String formattedDate = now.format(formatter);


		ChatRequest request = ChatRequest.builder()
				.roomidx(dto.getRoomidx())
				.email(dto.getEmail())
				.sender(member.getNickname())
				.msg(member.getNickname() + "님이 채팅방에 참여하였습니다.")
				.imageUrl(member.getProfileUrl())
				.time(formattedDate)
				.build();
		log.info("enter");
		// This will store the chat message in MongoDB
		chatService.recordHistory(request);

//		template.convertAndSend("/sub/chat/room/" + dto.getRoomidx(), dto);
		return request;
	}

	@MessageMapping(value = "/chat/{id}")
	@SendTo("/sub/chat/room/{id}")
	public ChatRequest message(ChatMessageDto dto) {

		Member member = memberRepository.findMemberByEmail(dto.getEmail()).orElseThrow(() ->
				new BusinessException(ErrorCode.NOT_EXISTS_USER_ID));

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 a hh:mm", Locale.KOREAN);
		String formattedDate = now.format(formatter);


		ChatRequest request = ChatRequest.builder()
				.roomidx(dto.getRoomidx())
				.email(dto.getEmail())
				.sender(member.getNickname())
				.msg(dto.getMessage())
				.imageUrl(member.getProfileUrl())
				.time(formattedDate)
				.build();

		log.info("send message");
		// This will store the chat message in MongoDB
		chatService.recordHistory(request);

//		template.convertAndSend("/sub/chat/room/" + dto.getRoomidx(), dto);
		log.info("received message");

		return request;
	}
}
