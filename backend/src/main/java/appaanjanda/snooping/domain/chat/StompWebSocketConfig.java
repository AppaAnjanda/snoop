package appaanjanda.snooping.domain.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /*
    endpoint 를 /stomp 로 하고, allowedOrigins 를 "*"로 하면 페이지에서
    Get /info 4040 Error 가 발생한다.
    그래서 아래와 같이 2개의 계층으로 분리하고 origins 를 개발 도메인으로 변경하지 잘 동작한다.
     */

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws")
			.setAllowedOriginPatterns("*");
	}

	// 어플리케이션 내부에서 사용할 path 를 지정할 수 있음
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/pub");
		registry.enableSimpleBroker("/sub");
	}
}
