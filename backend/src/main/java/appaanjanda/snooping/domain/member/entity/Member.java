package appaanjanda.snooping.domain.member.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import appaanjanda.snooping.domain.card.entity.MyCard;
import appaanjanda.snooping.domain.wishbox.entity.Wishbox;
import appaanjanda.snooping.global.common.BaseTimeEntity;
import appaanjanda.snooping.domain.member.entity.enumType.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String name;

	private String password;

	private String profileUrl;

	@Column(length = 20)
	private String email;

	@Enumerated(EnumType.STRING)
	private Role role;

	private String nickname;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<MyCard> myCardList;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<Wishbox> wishboxList;

	@Builder
	public Member(Long id, String name, String password, String profileUrl, String email, Role role, String nickname,
		List<MyCard> myCardList, List<Wishbox> wishboxList) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.profileUrl = profileUrl;
		this.email = email;
		this.role = role;
		this.nickname = nickname;
		this.myCardList = myCardList;
		this.wishboxList = wishboxList;
	}

	public void setNickname(String nickName) {
		this.nickname = nickName;
	}

	public void setUserPassword(String pw) {
		this.password = pw;
	}

	public void setProfileImageUrl(String upload) {
		this.profileUrl = upload;
	}

	public void setCardList(List<MyCard> myCardList) {
		this.myCardList = myCardList;
	}
}
