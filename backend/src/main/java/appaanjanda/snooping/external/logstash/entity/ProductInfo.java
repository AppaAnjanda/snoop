package appaanjanda.snooping.external.logstash.entity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductInfo {

    private String code;

    private String majorCategory;

    private String minorCategory;

    private String provider;

    private String message;

    private String productName;

    private String productLink;

    private String productImage;

    private int price;

    private String lastUpdate;
}
