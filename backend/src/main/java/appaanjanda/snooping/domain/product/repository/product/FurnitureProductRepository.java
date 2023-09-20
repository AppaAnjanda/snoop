package appaanjanda.snooping.domain.product.repository.product;

import appaanjanda.snooping.domain.product.entity.product.DigitalProduct;
import appaanjanda.snooping.domain.product.entity.product.FurnitureProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FurnitureProductRepository extends ElasticsearchRepository<FurnitureProduct, String> {

    Optional<FurnitureProduct> findByCode(String code);

    Optional<FurnitureProduct> findByProductName(String productName);
}
