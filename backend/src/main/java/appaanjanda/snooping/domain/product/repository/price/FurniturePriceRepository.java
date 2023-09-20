package appaanjanda.snooping.domain.product.repository.price;

import appaanjanda.snooping.domain.product.entity.price.FurniturePrice;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FurniturePriceRepository extends ElasticsearchRepository<FurniturePrice, String> {

    List<FurniturePrice> findSortedByCode(String code, Sort sort);
}
