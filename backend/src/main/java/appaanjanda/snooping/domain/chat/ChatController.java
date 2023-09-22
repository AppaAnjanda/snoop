// package appaanjanda.snooping.domain.chat;
//
// import java.util.UUID;
//
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
//
// import lombok.extern.slf4j.Slf4j;
//
// @Controller
// @Slf4j
// public class ChatController {
//
// 	@GetMapping("/chat")
// 	public String chatGet(Model model) {
//
// 		log.info("me.dragonappear.websocket.chat.ChatController.chatGet");
//
// 		model.addAttribute("name", UUID.randomUUID().toString());
// 		return "chat";
// 	}
// }
