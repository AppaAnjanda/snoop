package appaanjanda.snooping.domain.chat.room;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@Document(collection = "room")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Room {

	@Id
	@Field(name = "room_id")
	private String id;

	private int roomId;

	private String category;

	// @Field("created_at")
	@CreatedDate
	private LocalDateTime createdAt;

	// @Field("updated_at")
	@LastModifiedDate
	private LocalDateTime updatedAt;
}
