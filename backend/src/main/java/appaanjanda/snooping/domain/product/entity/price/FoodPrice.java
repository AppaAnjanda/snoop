package appaanjanda.snooping.domain.product.entity.price;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Document(indexName = "식품가격")
@Getter
@Setter
public class FoodPrice {

    @Id
    private String id;

    @Field(name = "code", type = FieldType.Text)
    private String code;

    @Field(name = "price", type = FieldType.Integer)
    private int price;

}
