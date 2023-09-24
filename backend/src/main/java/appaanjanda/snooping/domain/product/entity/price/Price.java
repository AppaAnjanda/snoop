package appaanjanda.snooping.domain.product.entity.price;

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
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Price implements PriceInterface {

    @Id
    private String id;

    @Field(name = "code", type = FieldType.Text)
    private String code;

    @Field(name = "price", type = FieldType.Integer)
    private int price;

    @Field(name = "@timestamp", type = FieldType.Date)
    private String timestamp;

}
