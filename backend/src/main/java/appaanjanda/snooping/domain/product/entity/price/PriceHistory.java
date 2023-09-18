package appaanjanda.snooping.domain.product.entity.price;

import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;

public class PriceHistory {
    @Field(type = FieldType.Text)
    private String price;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    private String timestamp;
}
