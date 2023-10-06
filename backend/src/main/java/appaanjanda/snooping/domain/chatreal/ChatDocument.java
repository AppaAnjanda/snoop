package appaanjanda.snooping.domain.chatreal;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Document(collection = "Chat")
@NoArgsConstructor
public class ChatDocument {

    @Id
    @Field(value = "_id", targetType = FieldType.OBJECT_ID)
    private String id;

    @Field("room_idx")
    private Long roomIdx;

    @Field("email")
    private String email;

    @Field("sender_name")
    private String senderName;

    @Field("msg")
    private String msg;

    @Field("imageUrl")
    private String imageUrl;

    @Field("created_at")
    private String createdAt;

    @Field("create_time")
    private LocalDateTime localDateTime;

    @Builder
    public ChatDocument(String id, Long roomIdx, String email, String senderName, String msg, String imageUrl, String createdAt, LocalDateTime localDateTime) {
        this.id = id;
        this.roomIdx = roomIdx;
        this.email = email;
        this.senderName = senderName;
        this.msg = msg;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.localDateTime = localDateTime;
    }
}
