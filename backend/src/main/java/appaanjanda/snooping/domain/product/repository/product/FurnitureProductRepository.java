package appaanjanda.snooping.domain.product.repository.product;

import appaanjanda.snooping.domain.product.entity.product.FurnitureProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FurnitureProductRepository extends ElasticsearchRepository<FurnitureProduct, String> {

    // code로 단일객체 찾기
    Optional<FurnitureProduct> findByCode(String code);

    // 상품명으로 찾기
    Optional<FurnitureProduct> findByProductName(String productName);
}
