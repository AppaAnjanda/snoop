package appaanjanda.snooping.external.logstash.controller;

import appaanjanda.snooping.external.logstash.entity.ProductInfo;
import appaanjanda.snooping.external.logstash.service.DigitalDataService;
import appaanjanda.snooping.external.logstash.service.FoodDataService;
import appaanjanda.snooping.external.logstash.service.FurnitureDataService;
import appaanjanda.snooping.external.logstash.service.NecessariesDataService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/logstash")
public class DataController {

    private final ObjectMapper objectMapper;
    private final DigitalDataService digitalDataService;
    private final FurnitureDataService furnitureDataService;
    private final NecessariesDataService necessariesDataService;
    private final FoodDataService foodDataService;

    // 디지털가전 데이터 처리
    @Operation(summary = "디지털/가전", description = "디지털가전 크롤링 데이터 처리", tags = { "Logstash-Data Controller" })
    @PostMapping("/digital")
    public void digitalToElasticsearch(@RequestBody JsonNode requestBody) {
        try {
            // json으로 파싱
            JsonNode parsedJson = requestBody.get("parsedJson");
            ProductInfo productInfo = objectMapper.treeToValue(parsedJson, ProductInfo.class);

            digitalDataService.checkPrice(productInfo);
        } catch (Exception e) {
            throw new RuntimeException("요청 에러", e);
        }
    }

    // 가구 데이터 처리
    @Operation(summary = "가구", description = "가구 크롤링 데이터 처리", tags = { "Logstash-Data Controller" })
    @PostMapping("/furniture")
    public void furnitureToElasticsearch(@RequestBody JsonNode requestBody) {
        try {
            // json으로 파싱
            JsonNode parsedJson = requestBody.get("parsedJson");
            ProductInfo productInfo = objectMapper.treeToValue(parsedJson, ProductInfo.class);

            furnitureDataService.checkPrice(productInfo);
        } catch (Exception e) {
            throw new RuntimeException("요청 에러", e);
        }
    }

    // 생활용품 데이터 처리
    @Operation(summary = "생활용품", description = "생활용품 크롤링 데이터 처리", tags = { "Logstash-Data Controller" })
    @PostMapping("/necessaries")
    public void necessariesToElasticsearch(@RequestBody JsonNode requestBody) {
        try {
            // json으로 파싱
            JsonNode parsedJson = requestBody.get("parsedJson");
            ProductInfo productInfo = objectMapper.treeToValue(parsedJson, ProductInfo.class);

            necessariesDataService.checkPrice(productInfo);
        } catch (Exception e) {
            throw new RuntimeException("요청 에러", e);
        }
    }

    // 식품 데이터 처리
    @Operation(summary = "식품", description = "식품 크롤링 데이터 처리", tags = { "Logstash-Data Controller" })
    @PostMapping("/food")
    public void foodToElasticsearch(@RequestBody JsonNode requestBody) {
        try {
            // json으로 파싱
            JsonNode parsedJson = requestBody.get("parsedJson");
            ProductInfo productInfo = objectMapper.treeToValue(parsedJson, ProductInfo.class);

            foodDataService.checkPrice(productInfo);
        } catch (Exception e) {
            throw new RuntimeException("요청 에러", e);
        }
    }
}
