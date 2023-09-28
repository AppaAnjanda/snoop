package appaanjanda.snooping.domain.firebase.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlertHistoryDto {
    private String title;
    private String body;

    @Builder
    public AlertHistoryDto(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
