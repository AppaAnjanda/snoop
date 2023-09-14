package appaanjanda.snooping.member.entity;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;

@RedisHash(value = "refreshToken")
public class RefreshToken {
	@Id
	private String refreshToken;
	private Long memberId;


	@Builder
	public RefreshToken(String refreshToken, Long memberId){
		this.refreshToken = refreshToken;
		this.memberId = memberId;
	}

	public String getRefreshToken(){
		return refreshToken;
	}

	public Long getMemberId(){
		return memberId;
	}
}