

package appaanjanda.snooping.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {

	private final String redisHost;
	private final int redisPort;

	public RedisConfig(@Value("${redis.host}") final String redisHost,
		@Value("${redis.port}") final int redisPort) {
		this.redisHost = redisHost;
		this.redisPort = redisPort;
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(redisHost, redisPort);
	}

	@Bean
	public RedisTemplate<?, ?> redisTemplate() {
		RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}
	//
	// /**
	//  * Redis 채팅을 위함.
	//  * @param connectionFactory
	//  * @param listenerAdapter
	//  * @param channelTopic
	//  * @return
	//  */
	// @Bean
	// public RedisMessageListenerContainer redisMessageListenerContainer( // (1)
	// 	RedisConnectionFactory connectionFactory,
	// 	MessageListenerAdapter listenerAdapter,
	// 	ChannelTopic channelTopic
	// ) {
	// 	RedisMessageListenerContainer container = new RedisMessageListenerContainer();
	// 	container.setConnectionFactory(connectionFactory);
	// 	container.addMessageListener(listenerAdapter, channelTopic);
	// 	return container;
	// }
	//
	// @Bean
	// public MessageListenerAdapter listenerAdapter(RedisSubscriber subscriber) { // (2)
	// 	return new MessageListenerAdapter(subscriber, "onMessage");
	// }
	//
	// @Bean
	// public RedisTemplate<String, Object> redisTemplate
	// 	(RedisConnectionFactory connectionFactory) { // (3)
	// 	RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
	// 	redisTemplate.setConnectionFactory(connectionFactory);
	// 	redisTemplate.setKeySerializer(new StringRedisSerializer());
	// 	redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
	// 	return redisTemplate;
	// }
	//
	// @Bean
	// public ChannelTopic channelTopic() { // (4)
	// 	return new ChannelTopic("chatroom");
	// }

	// @Bean
	// public RedisTemplate<String, Object> redisTemplate() {
	// 	RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
	// 	redisTemplate.setConnectionFactory(redisConnectionFactory());
	// 	redisTemplate.setKeySerializer(new StringRedisSerializer());
	// 	redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
	// 	return redisTemplate;
	// }
}