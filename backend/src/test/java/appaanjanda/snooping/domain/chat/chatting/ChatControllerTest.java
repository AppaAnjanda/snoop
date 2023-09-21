package appaanjanda.snooping.domain.chat.chatting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@WebFluxTest(ChatController.class)
public class ChatControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ChatRepository chatRepository;

    @MockBean
    private ChatService chatService;

    @Test
    public void testSetMsg() {
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setId("1");
        chatHistory.setRoomNum(1);
        chatHistory.setSenderName("testUser");
        chatHistory.setMsg("test message");
        chatHistory.setCreatedAt(LocalDateTime.now());

        Mockito.when(chatRepository.save(Mockito.any(ChatHistory.class)))
                .thenReturn(Mono.just(chatHistory));

        webTestClient.post()
                .uri("/chat")
                .bodyValue(chatHistory)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.roomNum").isEqualTo(1)
                .jsonPath("$.senderName").isEqualTo("testUser")
                .jsonPath("$.msg").isEqualTo("test message")
                .jsonPath("$.createdAt").isNotEmpty();
    }


    @Test
    public void testFindInitialMessages() {
        ChatHistory chatHistory1 = new ChatHistory();
        chatHistory1.setId("1");
        chatHistory1.setRoomNum(1);
        chatHistory1.setSenderName("testUser1");
        chatHistory1.setMsg("test message1");
        chatHistory1.setCreatedAt(LocalDateTime.now());

        ChatHistory chatHistory2 = new ChatHistory();
        chatHistory2.setId("2");
        chatHistory2.setRoomNum(1);
        chatHistory2.setSenderName("testUser2");
        chatHistory2.setMsg("test message2");
        chatHistory2.setCreatedAt(LocalDateTime.now());

        Mockito.when(chatRepository.findTop50ByRoomNumOrderByCreatedAtDesc(1))
                .thenReturn(Flux.just(chatHistory1, chatHistory2));

        webTestClient.get()
                .uri("/chat/roomNum/1/initial")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ChatHistory.class)
                .hasSize(2);
    }

}
