package appaanjanda.snooping.domain.wishbox.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddAlertRequestDto {
    private int alertPrice;

    @Builder
    public AddAlertRequestDto(int alertPrice) {
        this.alertPrice = alertPrice;
    }
}
