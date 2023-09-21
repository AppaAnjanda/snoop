package appaanjanda.snooping.domain.search.controller;


import appaanjanda.snooping.domain.member.service.dto.UserResponse;
import appaanjanda.snooping.domain.search.service.SearchService;
import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {

    private final SearchService searchService;

    // 카테고리로 상품 검색
    @Operation(summary = "카테고리로 검색", description = "대분류와 소분류 입력", tags = { "Search Controller" })
    @GetMapping("/{major}/{minor}")
    public List<?> getProductByCategory(@PathVariable String major, @PathVariable String minor) {

        return searchService.searchProductByCategory(major, minor);
    }

    /**
     * 분기 어떻게 태울지
     * @return
     */
    // 키워드로 상품 검색
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "키워드로 검색", description = "검색하고 싶은 단어 입력", tags = { "Search Controller" })
    @GetMapping("/{keyword}")
    public List<?> getProductByKeyword(@PathVariable String keyword, @MemberInfo MembersInfo membersInfo) {
        // 검색 기록 추가
        searchService.updateSearchHistory(keyword, membersInfo.getId());

        return searchService.searchProductByKeyword(keyword);
    }

    @Operation(summary = "키워드로 검색(게스트)", description = "검색하고 싶은 단어 입력", tags = { "Search Controller" })
    @GetMapping("/guest/{keyword}")
    public List<?> getProductByKeywordForGuest(@PathVariable String keyword) {

        return searchService.searchProductByKeyword(keyword);
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

        return ResponseEntity.ok(String.format("검색어 삭제 : %d", keyword));
    }


}
