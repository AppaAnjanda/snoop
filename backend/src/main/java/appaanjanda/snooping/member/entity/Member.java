package appaanjanda.snooping.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import appaanjanda.snooping.global.common.BaseTimeEntity;
import appaanjanda.snooping.member.entity.enumType.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Users")
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "memberId")
	private Long id;

	private String name;

	@Column(length = 20)
	private String email;

	@Enumerated(EnumType.STRING)
	private Role role;

	private String nickname;

	private String password;

	
	@Builder
	public Member(Long id, String name, String email, Role role, String nickname, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
		this.nickname = nickname;
		this.password = password;
	}

	public void setNickname(String nickName) {
		this.nickname = nickName;
	}

	public void setUserPassword(String pw) {
		this.password = pw;
	}
}
