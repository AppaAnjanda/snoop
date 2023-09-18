package appaanjanda.snooping.product.dto;

import appaanjanda.snooping.product.entity.product.BaseProduct;
import lombok.AllArgsConstructor;
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
