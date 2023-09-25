package appaanjanda.snooping.domain.chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

@Repository
public class ChatRoomRepository {
	private Map<String, ChatRoomDto> charRoomDtoMap;

	@PostConstruct
	private void init() {
		charRoomDtoMap = new LinkedHashMap<>();
	}

	public List<ChatRoomDto> findAllRooms() {
		// 채팅방 생성 순서 최근 순으로 반환
		ArrayList<ChatRoomDto> list = new ArrayList<>(charRoomDtoMap.values());
		Collections.reverse(list);

		return list;
	}

	public ChatRoomDto findRoomById(String id) {
		return charRoomDtoMap.get(id);
	}

	public ChatRoomDto createChatRoomDto(String name) {
		ChatRoomDto room = ChatRoomDto.create(name);
		charRoomDtoMap.put(room.getRoomId(), room);

		return room;
	}
}
