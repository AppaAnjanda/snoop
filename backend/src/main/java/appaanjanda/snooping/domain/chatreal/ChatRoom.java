package appaanjanda.snooping.domain.chatreal;

import appaanjanda.snooping.global.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomId")
    private Long id;

    private String roomName;

    @Builder
    public ChatRoom(Long id, String roomName) {
        this.id = id;
        this.roomName = roomName;
    }
}
