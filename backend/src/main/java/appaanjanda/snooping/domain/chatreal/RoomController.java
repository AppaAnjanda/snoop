package appaanjanda.snooping.domain.chatreal;

import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/chat")
@Slf4j
public class RoomController {

	private final ChatRoomRepository chatRoomRepository;
	private final ChatRepository chatRepository;

	// 채팅방 목록 조회
	@GetMapping("/rooms")
	public List<String> rooms() {
		log.info("# All Chat Rooms");

		List<ChatRoom> allRooms = chatRoomRepository.findAllRooms();

		List<String> names = new ArrayList<>();

		for (ChatRoom room : allRooms) {
			names.add(room.getRoomName());
		}

		return names;
	}

	// 채팅방 개설
	@PostMapping(value = "/room")
	public void create(@RequestBody String name) {
		log.info("# Create Chat Room, name: [{}]", name);

		ChatRoom chatRoom = ChatRoom.builder()
				.roomName(name)
				.build();

		chatRoomRepository.save(chatRoom);
	}

	// 채팅방 조회
	@SecurityRequirement(name = "Bearer Authentication")
	@Operation(summary = "Room 입장 후 이전 채팅 50개 보기", description = "Room 입장시 이전 50개의 채팅 목록 조회 ", tags = { "Room Controller" })
	@GetMapping("/room/{roomId}")
	public Page<ChatDocument> getRoom(@PathVariable Long roomId, @MemberInfo MembersInfo membersInfo, Pageable pageable) {
		log.info("# get Char Room, roomId = [{}]", roomId);
        return chatRepository.findByRoomIdxOrderByCreatedAtDesc(roomId, pageable);
	}
}

