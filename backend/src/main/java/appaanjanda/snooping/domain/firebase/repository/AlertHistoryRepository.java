package appaanjanda.snooping.domain.firebase.repository;


import appaanjanda.snooping.domain.firebase.entity.AlertHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertHistoryRepository extends JpaRepository<AlertHistory, Long> {
}
