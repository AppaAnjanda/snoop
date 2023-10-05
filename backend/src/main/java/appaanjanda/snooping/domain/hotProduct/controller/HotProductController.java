package appaanjanda.snooping.domain.hotProduct.controller;

import appaanjanda.snooping.domain.hotProduct.service.HotProductService;
import appaanjanda.snooping.domain.hotProduct.service.RecommendService;
import appaanjanda.snooping.domain.search.dto.SearchContentDto;
import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RequestMapping("/api/home")
@RestController
@RequiredArgsConstructor
public class HotProductController {

    private final HotProductService hotProductService;
    private final RecommendService recommendService;

    // 인기 상품
    @GetMapping("/hotProduct/digital")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "디지털가전 주간 인기 상품 조회", description = "최근 7일간의 디지털가전 분류 인기 상품", tags = { "Home Controller" })
    public List<SearchContentDto> getDigitalHotProduct(@MemberInfo(required = false) MembersInfo membersInfo) {
        return hotProductService.getHotProduct(membersInfo.getId(), "디지털가전");
    }

    @GetMapping("/hotProduct/furniture")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "가구 주간 인기 상품 조회", description = "최근 7일간의 가구 분류 인기 상품", tags = { "Home Controller" })
    public List<SearchContentDto> getFurnitureHotProduct(@MemberInfo(required = false) MembersInfo membersInfo) {
        return hotProductService.getHotProduct(membersInfo.getId(), "가구");
    }

    @GetMapping("/hotProduct/necessaries")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "생활용품 주간 인기 상품 조회", description = "최근 7일간의 생황용품 분류 인기 상품", tags = { "Home Controller" })
    public List<SearchContentDto> getNecessariesHotProduct(@MemberInfo(required = false) MembersInfo membersInfo) {
        return hotProductService.getHotProduct(membersInfo.getId(), "생활용품");
    }

    @GetMapping("/hotProduct/food")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "식품 주간 인기 상품 조회", description = "최근 7일간의 식품 분류 인기 상품", tags = { "Home Controller" })
    public List<SearchContentDto> getFoodHotProduct(@MemberInfo(required = false) MembersInfo membersInfo) {
        return hotProductService.getHotProduct(membersInfo.getId(), "식품");
    }

    @GetMapping("/recommend/byWishbox")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "찜 기반 상품 추천", description = "회원에게는 본인 찜 기반 추천, 비회원은 모든 찜 기반 추천", tags = { "Home Controller" })
    public List<SearchContentDto> getRecommendProduct(@MemberInfo(required = false) MembersInfo membersInfo) throws IOException {
        return recommendService.recommendByWishbox(membersInfo.getId());
    }


}
