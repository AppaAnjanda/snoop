package appaanjanda.snooping.domain.firebase.repository;


import appaanjanda.snooping.domain.firebase.entity.AlertHistory;
import appaanjanda.snooping.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertHistoryRepository extends JpaRepository<AlertHistory, Long> {
    List<AlertHistory> findAllByMemberOrderByCreateTimeDesc(Member member);
}
