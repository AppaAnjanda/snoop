package appaanjanda.snooping.domain.firebase.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AlertHistoryDto {
    private Long id;
    private String title;
    private String body;
    private String imageUrl;
    private String productCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Builder
    public AlertHistoryDto(Long alertId, String title, String body, String imageUrl, String productCode, LocalDateTime time) {
        this.id = alertId;
        this.title = title;
        this.body = body;
        this.createTime = time;
        this.imageUrl = imageUrl;
        this.productCode = productCode;
    }
}
