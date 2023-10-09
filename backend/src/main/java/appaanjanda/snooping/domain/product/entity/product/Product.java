package appaanjanda.snooping.domain.product.entity.product;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Product implements ProductInterface {

    @Id
    private String id;

    @Field(name = "code", type = FieldType.Text)
    private String code;

    @Field(name = "major_category", type = FieldType.Text)
    private String majorCategory;

    @Field(name = "minor_category", type = FieldType.Text)
    private String minorCategory;

    @Field(name = "provider", type = FieldType.Text)
    private String provider;

    @Field(name = "price", type = FieldType.Integer)
    private int price;

    @Field(name = "product_name", type = FieldType.Text)
    private String productName;

    @Field(name = "product_link", type = FieldType.Text)
    private String productLink;

    @Field(name = "product_image", type = FieldType.Text)
    private String productImage;

    @Field(name = "@timestamp", type = FieldType.Date)
    private String timestamp;
}
