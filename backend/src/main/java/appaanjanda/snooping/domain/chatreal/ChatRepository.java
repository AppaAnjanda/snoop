package appaanjanda.snooping.domain.chatreal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<ChatDocument, Long> {

    Page<ChatDocument> findByRoomIdxOrderByCreatedAtDesc(Long roomIdx, Pageable pageable);
}

