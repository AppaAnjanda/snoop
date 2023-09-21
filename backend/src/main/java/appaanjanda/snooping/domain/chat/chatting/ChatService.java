package appaanjanda.snooping.domain.chat.chatting;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;


import appaanjanda.snooping.domain.chat.chatting.dto.ChatSaveDto;

import appaanjanda.snooping.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatRepository chatRepository;
	private final MemberRepository memberRepository;


	public ChatHistory save (ChatSaveDto dto, Long id, int roomId){

		ChatHistory chatHistory = ChatHistory.builder()
			.senderName(memberRepository.findById(id).orElseThrow().getNickname())
			.roomIdx(roomId)
			.msg(dto.getContent())
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();

		chatRepository.save(chatHistory);
	}
}
