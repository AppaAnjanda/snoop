package appaanjanda.snooping.domain.chat;


import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.socket.WebSocketSession;

import lombok.Data;

@Data
public class ChatRoomDto {
	private String roomId;
	private String name;
	private Set<WebSocketSession> socketSessions = new HashSet<>();
	// WebSocketSession 은 Spring 에서 Websocket Connection 이 맺어진 세션

	public static ChatRoomDto create(String name) {
		ChatRoomDto room = new ChatRoomDto();
		room.setRoomId(UUID.randomUUID().toString());
		room.setName(name);
		return room;
	}
}
