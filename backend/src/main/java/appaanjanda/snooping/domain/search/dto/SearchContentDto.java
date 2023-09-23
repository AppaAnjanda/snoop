package appaanjanda.snooping.domain.search.dto;

import appaanjanda.snooping.domain.product.entity.product.Product;
import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class SearchContentDto extends Product { // 검색 결과 상품

    private boolean wishYn;

}
