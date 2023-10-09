package appaanjanda.snooping.domain.chatreal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

	@Query("select m from ChatRoom  m")
	List<ChatRoom> findAllRooms();

	Optional<ChatRoom> findChatRoomById(Long id);

}
