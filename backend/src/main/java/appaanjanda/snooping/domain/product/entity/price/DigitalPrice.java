package appaanjanda.snooping.domain.product.entity.price;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Document(indexName = "디지털가전가격")
@Getter
@Setter
@NoArgsConstructor
public class DigitalPrice {

    @Id
    private String id;

    @Field(name = "code", type = FieldType.Text)
    private String code;

    @Field(name = "price", type = FieldType.Integer)
    private int price;

    public DigitalPrice(String code, int price) {
        this.code = code;
        this.price = price;
    }
}
