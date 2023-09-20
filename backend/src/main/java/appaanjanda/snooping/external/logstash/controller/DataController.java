package appaanjanda.snooping.external.logstash.controller;

import appaanjanda.snooping.external.logstash.entity.ProductInfo;
import appaanjanda.snooping.external.logstash.service.DigitalDataService;
import appaanjanda.snooping.external.logstash.service.FoodDataService;
import appaanjanda.snooping.external.logstash.service.FurnitureDataService;
import appaanjanda.snooping.external.logstash.service.NecessariesDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logstash")
public class DataController {

    private final DigitalDataService digitalDataService;
    private final FurnitureDataService furnitureDataService;

    // 디지털가전 데이터 처리
    @PostMapping("/digital")
    public void digitalToElasticsearch(@RequestBody ProductInfo productInfo) {
        try {
            digitalDataService.checkPrice(productInfo);
        } catch (Exception e) {
            throw new RuntimeException("요청 에러", e);
        }
    }

    // 가구 데이터 처리
    @PostMapping("/furniture")
    public void furnitureToElasticsearch(@RequestBody ProductInfo productInfo) {
        try {
            furnitureDataService.checkPrice(productInfo);
        } catch (Exception e) {
            throw new RuntimeException("요청 에러", e);
        }
    }

    // 생활용품 데이터 처리
    @PostMapping("/necessaries")
    public void necessariesToElasticsearch(@RequestBody ProductInfo productInfo) {
//        try {
//            necessariesDataService.checkPrice(productInfo);
//        } catch (Exception e) {
//            throw new RuntimeException("요청 에러", e);
//        }
    }

    // 식품 데이터 처리
    @PostMapping("/food")
    public void foodToElasticsearch(@RequestBody ProductInfo productInfo) {
//        try {
//            foodDataService.checkPrice(productInfo);
//        } catch (Exception e) {
//            throw new RuntimeException("요청 에러", e);
//        }
    }
}
