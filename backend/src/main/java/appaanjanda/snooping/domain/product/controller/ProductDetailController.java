package appaanjanda.snooping.domain.product.controller;


import appaanjanda.snooping.domain.member.service.dto.UserResponse;
import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import appaanjanda.snooping.domain.product.service.ProductSearchService;
import appaanjanda.snooping.domain.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductDetailController {

    private final ProductService productService;
    private final ProductSearchService productSearchService;

    // 상품 상세 조회
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "요청 오류 "),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "상품 상세 조회", description = "상품id로 정보 조회", tags = { "Product Controller" })
    @GetMapping("/{productCode}")
    public Object getProductDetail(@PathVariable String productCode, @MemberInfo MembersInfo membersInfo){

        // 최근 본 상품 추가
        productService.updateRecentProduct(membersInfo.getId(), productCode);

        return productSearchService.searchProductById(productCode);

    }

    @Operation(summary = "상품 상세 조회(게스트)", description = "상품id로 정보 조회", tags = { "Product Controller" })
    @GetMapping("/guest/{productCode}")
    public Object getProductDetailForGuest(@PathVariable String productCode){

        return productSearchService.searchProductById(productCode);

    }

    // 주, 일, 시 가격 추이 조회
    @GetMapping("/graph/{productCode}/{period}")
    public List<?> getPriceHistory(@PathVariable String productCode, @PathVariable String period) {
        switch (period) {
            case "week":
                return productService.getPriceHistoryByWeek(productCode);
            case "day":
                return productService.getPriceHistoryByDay(productCode);
            case "hour":
                return productService.getPriceHistoryByHour(productCode);
            default:
                throw new IllegalArgumentException("Invalid index");
        }

    }

}
