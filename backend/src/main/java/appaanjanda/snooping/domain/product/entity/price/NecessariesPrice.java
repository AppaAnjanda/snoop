package appaanjanda.snooping.domain.product.entity.price;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Document(indexName = "생활용품가격")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NecessariesPrice implements PriceInterface {

    @Id
    private String id;

    @Field(name = "code", type = FieldType.Text)
    private String code;

    @Field(name = "price", type = FieldType.Integer)
    private int price;

    @Field(name = "@timestamp", type = FieldType.Date)
    private String timestamp;

    public NecessariesPrice(String code, int price, String date) {
        this.code = code;
        this.price = price;
        this.timestamp = date;
    }
}
