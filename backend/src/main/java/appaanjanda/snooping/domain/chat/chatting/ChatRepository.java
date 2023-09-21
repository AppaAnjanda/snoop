package appaanjanda.snooping.domain.chat.chatting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


//@Repository
//public interface ChatRepository extends MongoRepository<ChatHistory, String> {
//    Page<ChatHistory> findByRoomIdx(int roomIdx, Pageable pageable);
//}

public interface ChatRepository extends ReactiveMongoRepository<ChatHistory, String> {

    @Tailable
    @Query("{ roomNum: ?0 }")
    Flux<ChatHistory> mFindByRoomNum(Integer roomNum);

    Flux<ChatHistory> findTop50ByRoomNumOrderByCreatedAtDesc(Integer roomNum);

}
