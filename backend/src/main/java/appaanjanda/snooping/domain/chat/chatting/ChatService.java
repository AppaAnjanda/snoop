package appaanjanda.snooping.domain.chat.chatting;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import appaanjanda.snooping.domain.chat.chatting.dto.ChatSaveDto;
import appaanjanda.snooping.domain.chat.room.Room;
import appaanjanda.snooping.domain.chat.room.RoomRepository;
import appaanjanda.snooping.domain.member.repository.MemberRepository;
import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatRepository chatRepository;
	private final RoomRepository roomRepository;
	private final MemberRepository memberRepository;


	public ChatHistory save (ChatSaveDto dto, Long id, int roomId){

		roomRepository.findByRoomId(roomId).orElseThrow();

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
