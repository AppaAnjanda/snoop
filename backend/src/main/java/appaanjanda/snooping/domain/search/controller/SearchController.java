package appaanjanda.snooping.domain.search.controller;


import appaanjanda.snooping.domain.hotKeyword.service.HotKeywordService;
import appaanjanda.snooping.domain.search.dto.SearchResponseDto;
import appaanjanda.snooping.domain.search.service.SearchService;
import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final SearchService searchService;
    private final HotKeywordService hotKeywordService;

    // 카테고리로 상품 검색
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "카테고리로 검색", description = "대분류와 소분류 입력, page는 1부터", tags = { "Search Controller" })
    @GetMapping("/{major}/{minor}/{page}")
    public SearchResponseDto getProductByCategory(@PathVariable String major, @PathVariable String minor, @PathVariable int page,
                                                  @MemberInfo(required = false) MembersInfo membersInfo,
                                                  @RequestParam(value = "minPrice", defaultValue = "0") int minPrice,
                                                  @RequestParam(value = "maxPrice", defaultValue = "99999999") int maxPrice) {

        return searchService.searchProductByCategory(membersInfo.getId(), major, minor, page, minPrice, maxPrice);
    }

    // 키워드로 상품 검색
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "키워드로 검색", description = "검색하고 싶은 단어 입력", tags = { "Search Controller" })
    @GetMapping("/{keyword}/{page}")
    public SearchResponseDto getProductByKeyword(@PathVariable String keyword, @PathVariable int page,
                                                 @MemberInfo(required = false) MembersInfo membersInfo,
                                                 @RequestParam(value = "minPrice", defaultValue = "0") int minPrice,
                                                 @RequestParam(value = "maxPrice", defaultValue = "99999999") int maxPrice) throws UnsupportedEncodingException {

        //디코딩
        String decodedKeyword = URLDecoder.decode(keyword, StandardCharsets.UTF_8);
        // 회원이면 검색 기록 추가
        if (membersInfo.getId() != null) {
            searchService.updateSearchHistory(decodedKeyword, membersInfo.getId());
        }

        // 검색 횟수 증가
        hotKeywordService.updateHotKeyword(decodedKeyword);

        return searchService.searchProductByKeyword(decodedKeyword, page, minPrice, maxPrice, membersInfo.getId());
    }

    // 검색 기록 조회
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "검색기록", description = "검색창 진입시 검색 기록 최근 5개 반환", tags = { "Search Controller" })
    @GetMapping("/history")
    public List<String> getSearchHistory(@MemberInfo MembersInfo membersInfo) {

        return searchService.getSearchHistory(membersInfo.getId());
    }

    // 검색 기록 삭제
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "검색기록 삭제", description = "특정 검색 기록 삭제", tags = { "Search Controller" })
    @DeleteMapping("/history/{keyword}")
    public ResponseEntity<String> deleteSearchHistory(@MemberInfo MembersInfo membersInfo, @PathVariable String keyword) {

        searchService.deleteSearchHistory(keyword, membersInfo.getId());

        return ResponseEntity.ok(String.format("검색어 삭제 : %s", keyword));
    }


}
