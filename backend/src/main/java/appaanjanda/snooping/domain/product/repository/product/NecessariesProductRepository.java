package appaanjanda.snooping.domain.product.repository.product;

import appaanjanda.snooping.domain.product.entity.product.DigitalProduct;
import appaanjanda.snooping.domain.product.entity.product.NecessariesProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NecessariesProductRepository extends ElasticsearchRepository<NecessariesProduct, String> {

    Optional<NecessariesProduct> findByCode(String code);

    Optional<NecessariesProduct> findByProductName(String productName);
}
