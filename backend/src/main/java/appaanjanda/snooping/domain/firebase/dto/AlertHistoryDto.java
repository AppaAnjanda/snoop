package appaanjanda.snooping.domain.firebase.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlertHistoryDto {
    private Long id;
    private String title;
    private String body;

    @Builder
    public AlertHistoryDto(Long alertId, String title, String body) {
        this.id = alertId;
        this.title = title;
        this.body = body;
    }
}
