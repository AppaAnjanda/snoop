package appaanjanda.snooping.domain.product.repository.price;

import appaanjanda.snooping.domain.product.entity.price.FoodPrice;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodPriceRepository extends ElasticsearchRepository<FoodPrice, String> {

    // 정렬기준 추가해서 code로 가격 정보 리스트 찾기
    List<FoodPrice> findSortedByCode(String code, Sort sort);
}
