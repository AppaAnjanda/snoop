package appaanjanda.snooping.domain.product.repository;

import appaanjanda.snooping.domain.product.entity.product.FoodProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FoodProductRepository extends ElasticsearchRepository<FoodProduct, String> {
}
