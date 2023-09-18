package appaanjanda.snooping.domain.product.repository;

import appaanjanda.snooping.domain.product.entity.product.DigitalProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DigitalProductRepository extends ElasticsearchRepository<DigitalProduct, String> {
}
