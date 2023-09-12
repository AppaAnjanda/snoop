package appaanjanda.snooping.member.repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import appaanjanda.snooping.member.entity.RefreshToken;
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

	public void save(final RefreshToken refreshToken) {
		ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(refreshToken.getRefreshToken(), refreshToken.getMemberId());
		redisTemplate.expire(refreshToken.getRefreshToken(), REFRESH_TOKEN_VALID_TIME, TimeUnit.MILLISECONDS);

		log.info("Refresh token saved. Token: {}, Member ID: {}", refreshToken.getRefreshToken(), refreshToken.getMemberId());
	}


	public Optional<RefreshToken> findById(String refreshToken) {
		ValueOperations<String, Long> valueOperations = redisTemplate.opsForValue();
		Long memberId = valueOperations.get(refreshToken);

		if (Objects.isNull(memberId)) {
			return Optional.empty();
		}

		return Optional.of(new RefreshToken(refreshToken, memberId));
	}
}