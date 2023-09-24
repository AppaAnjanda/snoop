package appaanjanda.snooping.domain.wishbox.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddWishboxResponseDto {
    private String productCode;
    private int alertPrice;
    private Boolean alertYn;

    @Builder
    public AddWishboxResponseDto(String productCode, int alertPrice, Boolean alertYn) {
        this.productCode = productCode;
        this.alertPrice = alertPrice;
        this.alertYn = alertYn;
    }
}
