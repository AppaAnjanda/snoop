package appaanjanda.snooping.domain.wishbox.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WishboxResponseDto {
    private Long wishboxId;
    private String productCode;
    private String productName;
    private String productImage;
    private int price;
    private int alertPrice;
    private boolean alertYn;

    @Builder
    public WishboxResponseDto(Long wishboxId, String productCode, String productName, String productImage, int price, int alertPrice, boolean alertYn) {
        this.wishboxId = wishboxId;
        this.productCode = productCode;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.alertPrice = alertPrice;
        this.alertYn = alertYn;
    }
}
