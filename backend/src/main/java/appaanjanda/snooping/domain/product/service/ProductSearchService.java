package appaanjanda.snooping.domain.product.service;

import appaanjanda.snooping.domain.product.entity.price.*;
import appaanjanda.snooping.domain.product.entity.product.*;
import appaanjanda.snooping.domain.product.repository.product.DigitalProductRepository;
import appaanjanda.snooping.domain.product.repository.product.FoodProductRepository;
import appaanjanda.snooping.domain.product.repository.product.FurnitureProductRepository;
import appaanjanda.snooping.domain.product.repository.product.NecessariesProductRepository;
import appaanjanda.snooping.domain.search.dto.SearchContentDto;
import appaanjanda.snooping.domain.wishbox.entity.Wishbox;
import appaanjanda.snooping.domain.wishbox.repository.WishboxRepository;
import appaanjanda.snooping.global.error.code.ErrorCode;
import appaanjanda.snooping.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSearchService {

    private final DigitalProductRepository digitalProductRepository;
    private final FurnitureProductRepository furnitureProductRepository;
    private final NecessariesProductRepository necessariesProductRepository;
    private final FoodProductRepository foodProductRepository;
    private final WishboxRepository wishboxRepository;


    // 상품코드 별 가격 엔티티
    public Class<?> searchPriceById(String productCode) {
        // 123 에서 1(대분류 코드) 추출
        char index = productCode.charAt(0);

        switch (index) {
            case '1':
                return DigitalPrice.class;
            case '2':
                return FurniturePrice.class;
            case '3':
                return NecessariesPrice.class;
            case '4':
                return FoodPrice.class;
            default:
                throw new BusinessException(ErrorCode.NOT_EXISTS_CATEGORY);
        }
    }


    // 상품인덱스 별 상품 엔티티
    public Class<?> searchEntityByIndex(String index) {

        switch (index) {
            case "디지털가전":
                return DigitalProduct.class;
            case "가구":
                return FurnitureProduct.class;
            case "생활용품":
                return NecessariesProduct.class;
            case "식품":
                return FoodProduct.class;
            default:
                throw new BusinessException(ErrorCode.NOT_EXISTS_CATEGORY);
        }
    }

    //상품id로 조회
    public ProductInterface getProduct(String productCode) {
        // 123 에서 1(대분류 코드) 추출
        char index = productCode.charAt(0);

        switch (index) {
            case '1':
                return digitalProductRepository.findByCode(productCode).orElse(null);
            case '2':
                return furnitureProductRepository.findByCode(productCode).orElse(null);
            case '3':
                return necessariesProductRepository.findByCode(productCode).orElse(null);
            case '4':
                return foodProductRepository.findByCode(productCode).orElse(null);
            default:
                throw new BusinessException(ErrorCode.NOT_EXISTS_CATEGORY);
        }
    }

    // 상세 Dto 반환
    public SearchContentDto searchProductById(String productCode, Long memberId) {
        ProductInterface product = getProduct(productCode);

        boolean wishYn = false;
        boolean alertYn = false;
        if (product != null) {
            // 회원인경우 찜, 알림 여부 판단
            if (memberId != null) {
                // 현재 멤버 찜 목록
                Set<String> wishProductCode = wishboxRepository.findProductById(memberId);
                // 찜 여부
                if ((wishProductCode != null) && wishProductCode.contains(product.getCode())) wishYn = true;
                // 알림 여부
                if (wishYn) {
                    Wishbox findWishbox = wishboxRepository.findByProductCodeAndMemberId(productCode, memberId)
                            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_WISHBOX_ID));
                    if (findWishbox.getAlertYn()) alertYn = true;
                }
            }
            // Dto 생성
            return SearchContentDto.builder()
                    .id(product.getId())
                    .code(product.getCode())
                    .majorCategory(product.getMajorCategory())
                    .minorCategory(product.getMinorCategory())
                    .provider(product.getProvider())
                    .price(product.getPrice())
                    .productName(product.getProductName())
                    .productImage(product.getProductImage())
                    .productLink(product.getProductLink())
                    .timestamp(product.getTimestamp())
                    .wishYn(wishYn)
                    .alertYn(alertYn)
                    .build();
        } else {
            throw new BusinessException(ErrorCode.NOT_EXISTS_PRODUCT);
        }
    }

}

