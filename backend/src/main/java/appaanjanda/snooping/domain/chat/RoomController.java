package appaanjanda.snooping.domain.chat;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Slf4j
public class RoomController {

	private final ChatRoomRepository chatRoomRepository;

	// 채팅방 목록 조회
	@GetMapping("/rooms")
	public String rooms(Model model) {
		log.info("# All Chat Rooms");

		model.addAttribute("list", chatRoomRepository.findAllRooms());

		return "chat/rooms";
	}

	// 채팅방 개설
	@PostMapping(value = "/room")
	public String create(@RequestParam String name, RedirectAttributes redirectAttributes) {
		log.info("# Create Chat Room, name: [{}]", name);
		redirectAttributes.addFlashAttribute("roomName", chatRoomRepository.createChatRoomDto(name));
		return "redirect:/chat/rooms";
	}

	// 채팅방 조회
	@GetMapping("/room")
	public String getRoom(String roomId, Model model) {
		log.info("# get Char Room, roomId = [{}]", roomId);

		model.addAttribute("room", chatRoomRepository.findRoomById(roomId));
		model.addAttribute("username", UUID.randomUUID().toString());
		return "chat/room";
	}
}

