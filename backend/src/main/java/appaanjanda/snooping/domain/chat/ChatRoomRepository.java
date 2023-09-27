package appaanjanda.snooping.domain.chat;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

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
