package appaanjanda.snooping.domain.product.repository.product;

import appaanjanda.snooping.domain.product.entity.product.DigitalProduct;
import appaanjanda.snooping.domain.product.entity.product.FoodProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodProductRepository extends ElasticsearchRepository<FoodProduct, String> {

    Optional<FoodProduct> findByCode(String code);

    Optional<FoodProduct> findByProductName(String productName);
}
