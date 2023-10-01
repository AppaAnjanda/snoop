package appaanjanda.snooping.domain.chatreal;


import appaanjanda.snooping.domain.chat.ChatRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    @Transactional
    public void recordHistory(ChatRequest request) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 a hh:mm", Locale.KOREAN);
        String formattedDate = now.format(formatter);

        ChatDocument chatDocument = ChatDocument.builder()
                .roomIdx(request.getRoomidx())
                .email(request.getEmail())
                .senderName(request.getSender())
                .msg(request.getMsg())
                .imageUrl(request.getImageUrl())
                .createdAt(formattedDate)
                .localDateTime(LocalDateTime.now())
                .build();
        chatRepository.save(chatDocument);
    }
}
