package appaanjanda.snooping.domain.product.entity.product;


import appaanjanda.snooping.external.logstash.entity.ProductInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Document(indexName = "가구")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FurnitureProduct implements ProductInterface {

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

    public FurnitureProduct(ProductInfo productInfo) {
        this.majorCategory = productInfo.getMajorCategory();
        this.minorCategory = productInfo.getMinorCategory();
        this.provider = productInfo.getProvider();
        this.price = productInfo.getPrice();
        this.productName = productInfo.getProductName();
        this.productLink = productInfo.getProductLink();
        this.productImage = productInfo.getProductImage();
    }
}
