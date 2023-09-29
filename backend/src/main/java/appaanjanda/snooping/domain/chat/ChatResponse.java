package appaanjanda.snooping.domain.chat;



import lombok.*;

@NoArgsConstructor
@Getter
public class ChatResponse {

    private Long chatRoomNo;
    private String sender;
    private String msg;


    @Builder
    public ChatResponse(Long chatRoomNo, String sender, String senderUuid, String msg, String imgUrl) {
        this.chatRoomNo = chatRoomNo;
        this.sender = sender;
        this.msg = msg;
    }

    // 생성자, getter, setter 등이 필요하면 추가하세요.
}

