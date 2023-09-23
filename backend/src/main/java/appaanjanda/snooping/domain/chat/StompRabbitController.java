package appaanjanda.snooping.domain.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
@Controller
@RequiredArgsConstructor
@Slf4j
public class StompRabbitController {

    private final RabbitTemplate template;

    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
    private final static String CHAT_QUEUE_NAME = "chat.queue";

    @MessageMapping("chat.enter.{chatRoomId}")
    public void enter(ChatDto chatDto, @DestinationVariable String chatRoomId) {
        chatDto.setMessage("입장하셨습니다.");
        chatDto.setRegDate(LocalDateTime.now());

        // exchange
        template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chatDto);
        // template.convertAndSend("room." + chatRoomId, chat); //queue
        // template.convertAndSend("amq.topic", "room." + chatRoomId, chat); //topic
    }


    @MessageMapping("chat.message.{chatRoomId}")
    public void send(ChatDto chatDto, @DestinationVariable String chatRoomId) {
        chatDto.setRegDate(LocalDateTime.now());

        template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chatDto);
        //template.convertAndSend( "room." + chatRoomId, chat);
        //template.convertAndSend("amq.topic", "room." + chatRoomId, chat);
    }

    // receiver()는 단순히 큐에 들어온 메세지를 소비만 한다. (현재는 디버그 용도)
    @RabbitListener(queues = CHAT_QUEUE_NAME)
    public void receive(ChatDto chatDto) {
        log.info("chatDto.getMessage = {}",chatDto.getMessage());

    }
}