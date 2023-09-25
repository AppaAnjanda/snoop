package appaanjanda.snooping.domain.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PriceHistoryDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private int price;

    public PriceHistoryDto(LocalDateTime date, int price) {
        this.timestamp = date;
        this.price = price;
    }
}
