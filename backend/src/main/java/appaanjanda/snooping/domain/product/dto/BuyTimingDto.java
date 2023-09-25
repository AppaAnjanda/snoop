package appaanjanda.snooping.domain.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BuyTimingDto {

    private int avgPrice;
    private int curPrice;
    private double diffPercent;
    private String timing;

    public BuyTimingDto(int avgPrice, int curPrice, double diffPercent, String timing) {
        this.avgPrice = avgPrice;
        this.curPrice = curPrice;
        this.diffPercent = diffPercent;
        this.timing = timing;
    }
}
