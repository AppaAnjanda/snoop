package appaanjanda.snooping.domain.product.repository.price;

import appaanjanda.snooping.domain.product.entity.price.DigitalPrice;
import appaanjanda.snooping.domain.product.entity.price.NecessariesPrice;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NecessariesPriceRepository extends ElasticsearchRepository<NecessariesPrice, String> {

    // 정렬기준 추가해서 code로 가격 정보 리스트 찾기
    List<NecessariesPrice> findSortedByCode(String code, Sort sort);
}
