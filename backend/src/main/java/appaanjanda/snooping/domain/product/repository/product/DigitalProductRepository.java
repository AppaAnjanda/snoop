package appaanjanda.snooping.domain.product.repository.product;

import appaanjanda.snooping.domain.product.entity.product.DigitalProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DigitalProductRepository extends ElasticsearchRepository<DigitalProduct, String> {

    Optional<DigitalProduct> findByCode(String code);

    Optional<DigitalProduct> findByProductName(String productName);
}
