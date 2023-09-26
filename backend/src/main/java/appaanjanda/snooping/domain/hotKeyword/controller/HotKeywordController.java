package appaanjanda.snooping.domain.hotKeyword.controller;

import appaanjanda.snooping.domain.hotKeyword.service.HotKeywordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/home")
@RestController
@RequiredArgsConstructor
public class HotKeywordController {

    private final HotKeywordService hotKeywordService;

    // 인기 검색어
    @GetMapping("/hotKeyword")
    @Operation(summary = "인기 검색어 조회", description = "최근 7일간의 검색횟수 인기 검색어", tags = { "Home Controller" })
    public List<String> getHotKeyword() {
        return hotKeywordService.getHotKeyword();
    }
}
