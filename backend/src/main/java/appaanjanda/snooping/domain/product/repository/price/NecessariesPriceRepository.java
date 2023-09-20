package appaanjanda.snooping.domain.product.repository.price;

import appaanjanda.snooping.domain.product.entity.price.NecessariesPrice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NecessariesPriceRepository extends ElasticsearchRepository<NecessariesPrice, String> {
}
