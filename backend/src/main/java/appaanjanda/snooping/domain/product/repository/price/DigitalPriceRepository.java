package appaanjanda.snooping.domain.product.repository.price;

import appaanjanda.snooping.domain.product.entity.price.DigitalPrice;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DigitalPriceRepository extends ElasticsearchRepository<DigitalPrice, String> {

    // 정렬기준 추가해서 code로 가격 정보 리스트 찾기
    List<DigitalPrice> findSortedByCode(String code, Sort sort);
}
