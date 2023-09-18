package appaanjanda.snooping.search.controller;


import appaanjanda.snooping.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    // 카테고리로 상품 검색
    @GetMapping("/{major}/{minor}")
    public List<?> getProductByCategory(@PathVariable String major, @PathVariable String minor) {

        return searchService.searchProductByCategory(major, minor);
    }

    // 키워드로 상품 검색
    // TODO 검색어 RDB에 저장
    @GetMapping("/{keyword}")
    public List<?> getProductByKeyword(@PathVariable String keyword) {

        return searchService.searchProductByKeyword(keyword);
    }
}
