package appaanjanda.snooping.domain.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDetailDto {
    private Object content;
//    private boolean isWishList;

    public ProductDetailDto(Object productSource) {
        this.content = productSource;
    }
}
