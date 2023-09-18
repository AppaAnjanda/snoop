package appaanjanda.snooping.domain.search.controller;


import appaanjanda.snooping.domain.search.dto.SearchHistoryDto;
import appaanjanda.snooping.domain.search.entity.SearchHistory;
import appaanjanda.snooping.domain.search.service.SearchService;
import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final SearchService searchService;

    // 카테고리로 상품 검색
    @GetMapping("/{major}/{minor}")
    public List<?> getProductByCategory(@PathVariable String major, @PathVariable String minor) {

        return searchService.searchProductByCategory(major, minor);
    }

    // 키워드로 상품 검색
    @GetMapping("/{keyword}")
    public List<?> getProductByKeyword(@PathVariable String keyword, @MemberInfo MembersInfo membersInfo) {
        // 검색 기록 추가
        searchService.updateSearchHistory(keyword, membersInfo.getId());

        return searchService.searchProductByKeyword(keyword);
    }

    // 검색 기록 조회
    @GetMapping("/")
    public List<String> getSearchHistory(@MemberInfo MembersInfo membersInfo) {

        return searchService.getSearchHistory(membersInfo.getId());
    }
}
