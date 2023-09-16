package appaanjanda.snooping.product.controller;


import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import appaanjanda.snooping.member.entity.Member;
import appaanjanda.snooping.product.dto.ProductDetailDto;
import appaanjanda.snooping.product.service.ProductSearchService;
import appaanjanda.snooping.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductDetailController {

    private final ProductService productService;
    private final ProductSearchService productSearchService;

    // 상품 상세 조회
    @GetMapping("/{majorCategory}/{productId}")
    public ProductDetailDto getProductDetail(@PathVariable String majorCategory, @PathVariable String productId, @MemberInfo MembersInfo membersInfo){

        return productService.getProductById(membersInfo.getId(), majorCategory, productId);

    }
}
