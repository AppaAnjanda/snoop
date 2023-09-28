package appaanjanda.snooping.domain.chat;


import lombok.*;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class ChatRequest {

    private Long roomidx;
    private String sender;
    private String msg;
}

