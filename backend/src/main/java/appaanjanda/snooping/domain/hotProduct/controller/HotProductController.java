package appaanjanda.snooping.domain.hotProduct.controller;

import appaanjanda.snooping.domain.hotProduct.service.HotProductService;
import appaanjanda.snooping.domain.search.dto.SearchContentDto;
import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.monitor.os.OsStats;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/home")
@RestController
@RequiredArgsConstructor
public class HotProductController {

    private final HotProductService hotProductService;

    // 인기 상품
    @GetMapping("/hotProduct/digital")
    @Operation(summary = "디지털가전 인기 상품 조회", description = "최근 7일간의 디지털가전 분류 인기 상품", tags = { "Home Controller" })
    public List<SearchContentDto> getDigitalHotProduct(@MemberInfo(required = false)MembersInfo membersInfo) {
        return hotProductService.getHotProduct(membersInfo.getId(), "디지털가전");
    }

    @GetMapping("/hotProduct/furniture")
    @Operation(summary = "가구 인기 상품 조회", description = "최근 7일간의 가구 분류 인기 상품", tags = { "Home Controller" })
    public List<SearchContentDto> getFurnitureHotProduct(@MemberInfo(required = false)MembersInfo membersInfo) {
        return hotProductService.getHotProduct(membersInfo.getId(), "가구");
    }

    @GetMapping("/hotProduct/necessaries")
    @Operation(summary = "생활용품 인기 상품 조회", description = "최근 7일간의 생황용품 분류 인기 상품", tags = { "Home Controller" })
    public List<SearchContentDto> getNecessariesHotProduct(@MemberInfo(required = false)MembersInfo membersInfo) {
        return hotProductService.getHotProduct(membersInfo.getId(), "생활용품");
    }

    @GetMapping("/hotProduct/food")
    @Operation(summary = "식품 인기 상품 조회", description = "최근 7일간의 식품 분류 인기 상품", tags = { "Home Controller" })
    public List<SearchContentDto> getFoodHotProduct(@MemberInfo(required = false)MembersInfo membersInfo) {
        return hotProductService.getHotProduct(membersInfo.getId(), "식품");
    }
}
