package appaanjanda.snooping.domain.wishbox.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddWishboxResponseDto {
    private String productId;
    private int alertPrice;
    private Boolean alertYn;

    @Builder
    public AddWishboxResponseDto(String productId, int alertPrice, Boolean alertYn) {
        this.productId = productId;
        this.alertPrice = alertPrice;
        this.alertYn = alertYn;
    }
}
