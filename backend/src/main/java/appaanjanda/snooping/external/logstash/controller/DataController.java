package appaanjanda.snooping.external.logstash.controller;

import appaanjanda.snooping.external.logstash.entity.ProductInfo;
import appaanjanda.snooping.external.logstash.service.DigitalDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/data")
public class DataController {

    private final DigitalDataService digitalDataService;

    @RequestMapping("/digital")
    public void digitalToElasticsearch(@RequestBody ProductInfo productInfo) {
        digitalDataService.checkPrice(productInfo);
    }

    @RequestMapping("/furniture")
    public void furnitureToElasticsearch(@RequestBody ProductInfo productInfo) {


    }

    @RequestMapping("/necessaries")
    public void necessariesToElasticsearch(@RequestBody ProductInfo productInfo) {


    }

    @RequestMapping("/food")
    public void foodToElasticsearch(@RequestBody ProductInfo productInfo) {


    }
}
