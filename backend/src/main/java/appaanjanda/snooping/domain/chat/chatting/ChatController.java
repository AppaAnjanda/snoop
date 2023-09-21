package appaanjanda.snooping.domain.chat.chatting;



import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import appaanjanda.snooping.domain.chat.chatting.dto.ChatSaveDto;
import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // DI
@RestController // 데이터 리턴 서버
public class ChatController {

	// DI
	private final ChatRepository chatRepository;
	private final ChatService chatService;

	@DeleteMapping("chat/{id}")
	public int deleteById(@PathVariable String id) {

		chatRepository.deleteById(id);

		return 1; // 1 : 성공, -1 : 실패
	}

	@GetMapping("/chat/{id}")
	public ChatHistory findById(@PathVariable String id) {
		return chatRepository.findById(id).get();
	}

	@GetMapping("/chat/{roomId}")
	public List<ChatHistory> findAll(@PathVariable int roomId) { // 리턴을 JavaObject로 하면 스프링 내부적으로 JSON으로 자동 변환 해준다.
		return chatRepository.findAll();
	}

	@PostMapping("/chat/{roomId}")
	public ChatHistory save(@RequestBody ChatSaveDto dto, @MemberInfo MembersInfo membersInfo,
		@PathVariable int roomId) { // {"title":"제목3","content":"내용3"}
		return chatService.save(dto, membersInfo.getId(), roomId);
	}
}
