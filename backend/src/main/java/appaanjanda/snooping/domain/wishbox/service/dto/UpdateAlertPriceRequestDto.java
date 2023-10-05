package appaanjanda.snooping.domain.wishbox.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateAlertPriceRequestDto {
    private int alertPrice;

    @Builder
    public UpdateAlertPriceRequestDto(int alertPrice) {
        this.alertPrice = alertPrice;
    }
}
