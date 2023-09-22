package appaanjanda.snooping.domain.product.entity.product;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Field(name = "code", type = FieldType.Text)
    public String code;

    @Field(name = "major_category", type = FieldType.Text)
    public String majorCategory;

    @Field(name = "minor_category", type = FieldType.Text)
    public String minorCategory;

    @Field(name = "provider", type = FieldType.Text)
    public String provider;

    @Field(name = "message", type = FieldType.Text)
    public String message;

    @Field(name = "price", type = FieldType.Integer)
    public int price;

    @Field(name = "product_name", type = FieldType.Text)
    private String productName;

    @Field(name = "product_link", type = FieldType.Text)
    private String productLink;

    @Field(name = "product_image", type = FieldType.Text)
    private String productImage;

    @Field(name = "last_update", type = FieldType.Text)
    private String lastUpdate;
}
