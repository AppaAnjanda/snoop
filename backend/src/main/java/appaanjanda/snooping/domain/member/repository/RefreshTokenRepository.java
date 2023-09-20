package appaanjanda.snooping.domain.member.repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import appaanjanda.snooping.domain.member.entity.AccessToken;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class RefreshTokenRepository {

	@Value("${jwt.atk}")
	private Long TOKEN_VALID_TIME;

	@Value("${jwt.rtk}")
	private Long REFRESH_TOKEN_VALID_TIME;

	private final RedisTemplate<String, Long> redisTemplate;

	public RefreshTokenRepository(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void save(final AccessToken accessToken) {
		ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(accessToken.getAccessToken(), accessToken.getMemberId());
		redisTemplate.expire(accessToken.getAccessToken(), REFRESH_TOKEN_VALID_TIME, TimeUnit.MILLISECONDS);

		log.info("Refresh token saved. Token: {}, Member ID: {}", accessToken.getAccessToken(), accessToken.getMemberId());
	}


	public Optional<AccessToken> findById(String refreshToken) {
		ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
		Long memberId = valueOperations.get(refreshToken);

		if (Objects.isNull(memberId)) {
			return Optional.empty();
		}

		return Optional.of(new AccessToken(refreshToken, memberId));
	}
}