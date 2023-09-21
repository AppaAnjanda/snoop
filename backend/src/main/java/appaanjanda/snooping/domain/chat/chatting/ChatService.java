package appaanjanda.snooping.domain.chat.chatting;

import java.time.LocalDateTime;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import appaanjanda.snooping.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatRepository chatRepository;
	private final MemberRepository memberRepository;


	public ChatHistory save (ChatSaveDto dto, Long id, int roomId){
		log.info("roomId={}", roomId);
		ChatHistory chatHistory = ChatHistory.builder()
			.senderName(memberRepository.findById(id).orElseThrow().getNickname())
			.roomNum(roomId)
			.msg(dto.getContent())
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();

		chatRepository.save(chatHistory);
		log.info("why ={}", chatHistory);
		return chatHistory;
	}
}
