package appaanjanda.snooping.domain.recentProduct.controller;

import appaanjanda.snooping.domain.recentProduct.service.RecentProductService;
import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class RecentProductController {

    private final RecentProductService recentProductService;

    // 최근 본 상품 조회
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "최근 본 상품 조회", description = "최근 본 상품 10개", tags = { "Product Controller" })
    @GetMapping("/recent")
    public List<Object> getRecentProduct(@MemberInfo MembersInfo membersInfo) {

        return recentProductService.getRecentProduct(membersInfo.getId());
    }
}
