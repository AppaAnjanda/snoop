package appaanjanda.snooping.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDto {
    private String name;
    private String link;
    private int price;
    private String image;
    private String provider;

    public ProductDetailDto(Map<String, Object> productSource, int price) {
        this.name = (String) productSource.get("product_name");
        this.link = (String) productSource.get("product_link");
        this.price = price;
        this.image = (String) productSource.get("product_image");
        this.provider = (String) productSource.get("provider");
    }

}
