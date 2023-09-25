package appaanjanda.snooping.domain.member.service.dto;

import java.util.List;

import appaanjanda.snooping.domain.card.entity.MyCard;
import appaanjanda.snooping.domain.member.entity.enumType.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

	private String email;

	private String nickname;

	private String profileUrl;
}
