package appaanjanda.snooping.domain.member.entity;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;

@RedisHash(value = "accessToken")
public class AccessToken {
	@Id
	private String accessToken;
	private Long memberId;


	@Builder
	public AccessToken(String accessToken, Long memberId){
		this.accessToken = accessToken;
		this.memberId = memberId;
	}

	public String getAccessToken(){
		return accessToken;
	}

	public Long getMemberId(){
		return memberId;
	}
}