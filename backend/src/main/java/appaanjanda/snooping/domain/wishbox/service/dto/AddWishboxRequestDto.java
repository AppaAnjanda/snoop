package appaanjanda.snooping.domain.wishbox.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddWishboxRequestDto {
    private int alertPrice;

    @Builder
    public AddWishboxRequestDto(int alertPrice) {
        this.alertPrice = alertPrice;
    }
}
