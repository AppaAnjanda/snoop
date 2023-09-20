package appaanjanda.snooping.domain.product.entity.product;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Document(indexName = "가구")
@Getter
@Setter
public class FurnitureProduct {

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

    @Field(name = "message", type = FieldType.Text)
    private String message;

    @Field(name = "price", type = FieldType.Integer)
    private int price;

    @Field(name = "product_name", type = FieldType.Text)
    private String productName;

    @Field(name = "product_link", type = FieldType.Text)
    private String productLink;

    @Field(name = "product_image", type = FieldType.Text)
    private String productImage;

    @Field(name = "last_update", type = FieldType.Text)
    private String lastUpdate;
}
