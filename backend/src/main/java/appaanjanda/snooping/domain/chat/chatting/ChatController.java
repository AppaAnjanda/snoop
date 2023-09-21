package appaanjanda.snooping.domain.chat.chatting;



import java.time.LocalDateTime;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RequiredArgsConstructor // DI
@RestController // 데이터 리턴 서버
public class ChatController {

	// DI
	private final ChatRepository chatRepository;
	private final ChatService chatService;

	@CrossOrigin
	@PostMapping("/chat")
	public Mono<ChatHistory> setMsg(@RequestBody ChatHistory chat){
		chat.setCreatedAt(LocalDateTime.now());
		return chatRepository.save(chat); // Object를 리턴하면 자동으로 JSON 변환 (MessageConverter)
	}

	@CrossOrigin
	@GetMapping(value = "/chat/roomNum/{roomNum}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ChatHistory> findByRoomNum(@PathVariable Integer roomNum) {
		return chatRepository.mFindByRoomNum(roomNum)
				.subscribeOn(Schedulers.boundedElastic());
	}

	@CrossOrigin
	@GetMapping("/chat/roomNum/{roomNum}/initial")
	public Flux<ChatHistory> findInitialMessages(@PathVariable Integer roomNum) {
		return chatRepository.findTop50ByRoomNumOrderByCreatedAtDesc(roomNum);
	}

//
//	@DeleteMapping("chat/{id}")
//	public int deleteById(@PathVariable String id) {
//
//		chatRepository.deleteById(id);
//
//		return 1; // 1 : 성공, -1 : 실패
//	}
//
//	@GetMapping("/chat/{id}")
//	public ChatHistory findById(@PathVariable String id) {
//		return chatRepository.findById(id).get();
//	}
//
//	@GetMapping("/chat/{roomId}")
//	public Page<ChatHistory> findAll(@PathVariable int roomId, Pageable pageable) {
//		return chatRepository.findByRoomIdx(roomId, pageable);
//	}
//
//	@PostMapping("/chat/{roomId}")
//	public ChatHistory save(@RequestBody ChatSaveDto dto, @MemberInfo MembersInfo membersInfo,
//		@PathVariable int roomId) { // {"title":"제목3","content":"내용3"}
//		log.info("roomId={}", roomId);
//		return chatService.save(dto, membersInfo.getId(), roomId);
//	}
}
