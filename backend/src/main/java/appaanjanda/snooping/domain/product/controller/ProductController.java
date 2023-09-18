package appaanjanda.snooping.domain.product.controller;

import appaanjanda.snooping.domain.product.dto.ProductDetailDto;
import appaanjanda.snooping.domain.product.service.ProductService;
import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 최근 본 상품 조회
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "최근 본 상품 조회", description = "최근 본 상품 10개", tags = { "Product Controller" })
    @GetMapping("/recent")
    public List<Object> getRecentProduct(@MemberInfo MembersInfo membersInfo) {

        return productService.getRecentProduct(membersInfo.getId());
    }
}
