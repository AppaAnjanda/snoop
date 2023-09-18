package appaanjanda.snooping.domain.product.repository;

import appaanjanda.snooping.domain.product.entity.product.NecessariesProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NecessariesProductRepository extends ElasticsearchRepository<NecessariesProduct, String> {
}
