package appaanjanda.snooping.domain.product.controller;


import appaanjanda.snooping.domain.member.service.dto.UserResponse;
import appaanjanda.snooping.domain.product.dto.BuyTimingDto;
import appaanjanda.snooping.domain.product.dto.PriceHistoryDto;
import appaanjanda.snooping.domain.product.service.ProductDetailService;
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
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductDetailController {

    private final ProductService productService;
    private final ProductSearchService productSearchService;
    private final ProductDetailService productDetailService;

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
    public Object getProductDetail(@PathVariable String productCode, @MemberInfo(required = false) MembersInfo membersInfo) throws UnsupportedEncodingException {
        //디코딩
        String decodedProductCode = URLDecoder.decode(productCode, StandardCharsets.UTF_8.toString());
        Long memberId = membersInfo.getId();
        if (memberId != null) {
            // 최근 본 상품 추가
            productService.updateRecentProduct(membersInfo.getId(), decodedProductCode);
        }

        return productSearchService.searchProductById(decodedProductCode, memberId);
    }

    // 주, 일, 시 가격 추이 조회
    @GetMapping("/graph/{productCode}/{period}")
    @Operation(summary = "기간별 상품 가격 그래프", description = "'week', 'day', 'hour'을 period에 입력해서 기간별 가격 리스트 조회", tags = { "Product Controller" })
    public List<PriceHistoryDto> getPriceHistory(@PathVariable String productCode, @PathVariable String period) throws UnsupportedEncodingException {
        //디코딩
        String decodedProductCode = URLDecoder.decode(productCode, StandardCharsets.UTF_8.toString());
        // 단위 시간
        DateHistogramInterval interval;
        // 기간
        int cnt;

        switch (period) {
            case "week":
                interval = DateHistogramInterval.WEEK;
                cnt = 4;
                break;
            case "day":
                interval = DateHistogramInterval.DAY;
                cnt = 14;
                break;
            case "hour":
                interval = DateHistogramInterval.HOUR;
                cnt = 24;
                break;
            default:
                throw new IllegalArgumentException("Invalid index");
        }
        return productDetailService.productGraph(decodedProductCode, interval, cnt);
    }

    // 구매 타이밍
    @GetMapping("/timing/{productCode}")
    @Operation(summary = "구매 타이밍", description = "최근 30일간의 평균 가격과 비교 \n 평균가, 현재가, 가격차이 퍼센트, 7단계의 타이밍 제", tags = { "Product Controller" })
    public BuyTimingDto getButTiming(@PathVariable String productCode) throws UnsupportedEncodingException {
        //디코딩
        String decodedProductCode = URLDecoder.decode(productCode, StandardCharsets.UTF_8.toString());

        return productDetailService.buyTiming(decodedProductCode);
    }

}
