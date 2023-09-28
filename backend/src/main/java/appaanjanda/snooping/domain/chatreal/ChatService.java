package appaanjanda.snooping.domain.chatreal;


import appaanjanda.snooping.domain.chat.ChatRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    @Transactional
    public void recordHistory(ChatRequest request) {
        ChatDocument chatDocument = ChatDocument.builder()
                .roomIdx(request.getRoomidx())
                .senderName(request.getSender())
                .msg(request.getMsg())
                .createdAt(LocalDateTime.now())
                .build();
        chatRepository.save(chatDocument);
    }
}
