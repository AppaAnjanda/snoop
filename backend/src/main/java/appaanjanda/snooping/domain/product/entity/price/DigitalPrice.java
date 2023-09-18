package appaanjanda.snooping.domain.product.entity.price;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.util.List;

@Document(indexName = "디지털가전")
@Getter
@Setter
public class DigitalPrice {

    @Id
    private String id;

    @Field(name = "routing", type = FieldType.Text)
    private String routing;

    @Field(name = "price_history", type = FieldType.Nested)
    private List<PriceHistory> priceHistory;
}
