package appaanjanda.snooping.domain.hotProduct.entity;

import appaanjanda.snooping.global.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class HotProduct extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotProduct_id")
    private Long id;

    private String productCode;

    private String category;

    private int count;

    public HotProduct(String productCode, String category, int count) {
        this.productCode = productCode;
        this.category = category;
        this.count = count;
    }
}
