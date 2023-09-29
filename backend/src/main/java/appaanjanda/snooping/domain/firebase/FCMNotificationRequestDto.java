package appaanjanda.snooping.domain.firebase;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class FCMNotificationRequestDto {

    private Long memberId;
    private String title;
    private String body;
}
