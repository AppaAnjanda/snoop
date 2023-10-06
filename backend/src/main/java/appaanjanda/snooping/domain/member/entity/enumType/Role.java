package appaanjanda.snooping.domain.member.entity.enumType;

public enum Role {

	USER("유저", "USER"),
	ADMIN("관리자", "ADMIN");

	private final String key;
	private final String value;

	Role(String key, String value){
		this.key = key;
		this.value = value;
	}

	String getRole(String key){
		Role role = Role.valueOf(key);
		return role.key;
	}
}
