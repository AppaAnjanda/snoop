package appaanjanda.snooping.domain.product.repository;

import appaanjanda.snooping.domain.product.entity.product.FurnitureProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FurnitureProductRepository extends ElasticsearchRepository<FurnitureProduct, String> {
}
