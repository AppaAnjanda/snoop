package appaanjanda.snooping.domain.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/stomp/chat")
                .setAllowedOriginPatterns("http://*.*.*.*:8081", "http://*:8081") //안해도 무관
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setPathMatcher(new AntPathMatcher("."));  // url을 chat/room/3 -> chat.room.3으로 참조하기 위한 설정
        registry.setApplicationDestinationPrefixes("/pub");

        //registry.enableSimpleBroker("/sub");
        registry.enableStompBrokerRelay("/queue", "/topic", "/exchange", "/amq/queue");
    }
}
