package appaanjanda.snooping.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import appaanjanda.snooping.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);

	Optional<Member> findByEmailAndPassword(String email, String password);

}
