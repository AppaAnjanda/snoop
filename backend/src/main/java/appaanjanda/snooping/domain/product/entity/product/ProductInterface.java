package appaanjanda.snooping.domain.product.entity.product;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

public interface ProductInterface {

    String getId();
    String getCode();
    String getMajorCategory();
    String getMinorCategory();
    String getProvider();
    String getProductName();
    String getProductLink();
    String getProductImage();
    int getPrice();
    String getTimestamp();

}
