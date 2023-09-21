package appaanjanda.snooping.domain.chat.chatting;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.Id;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Getter
@Builder
@Setter
@AllArgsConstructor
@Document(collection = "chatting")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ChatHistory {

	@Id
	// @Field(value = "chatting_id", targetType = FieldType.OBJECT_ID)
	@Field(name = "chatHistory_id")
	private String id;

	// @Field("room_idx")
	private int roomNum;

	// @Field("sender_name")
	private String senderName;

	// @Field("msg")
	private String msg;

	// @Field("created_at")
	@CreatedDate
	private LocalDateTime createdAt;

	// @Field("updated_at")
	@LastModifiedDate
	private LocalDateTime updatedAt;

	public void setId(String id) {
		this.id = id;
	}

	public void setCreatedAt(LocalDateTime now) {
		this.createdAt = now;
	}
}
