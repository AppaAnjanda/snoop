package appaanjanda.snooping.domain.wishbox.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddAlertResponseDto {
    private Long wishboxId;
    private String productCode;
    private int alertPrice;
    private Boolean alertYn;
    private String provider;

    @Builder
    public AddAlertResponseDto(Long wishboxId, String productCode, int alertPrice, Boolean alertYn, String provider) {
        this.wishboxId = wishboxId;
        this.productCode = productCode;
        this.alertPrice = alertPrice;
        this.alertYn = alertYn;
        this.provider = provider;
    }
}
