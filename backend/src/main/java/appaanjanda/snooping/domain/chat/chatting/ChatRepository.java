package appaanjanda.snooping.domain.chat.chatting;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRepository extends MongoRepository<ChatHistory, String> {

}

