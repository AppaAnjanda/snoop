package appaanjanda.snooping.domain.product.service;

import appaanjanda.snooping.domain.product.entity.product.*;
import appaanjanda.snooping.domain.product.repository.product.DigitalProductRepository;
import appaanjanda.snooping.domain.product.repository.product.FoodProductRepository;
import appaanjanda.snooping.domain.product.repository.product.FurnitureProductRepository;
import appaanjanda.snooping.domain.product.repository.product.NecessariesProductRepository;
import appaanjanda.snooping.domain.search.dto.SearchContentDto;
import appaanjanda.snooping.domain.wishbox.repository.WishboxRepository;
import appaanjanda.snooping.global.error.code.ErrorCode;
import appaanjanda.snooping.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final DigitalProductRepository digitalProductRepository;
    private final FurnitureProductRepository furnitureProductRepository;
    private final NecessariesProductRepository necessariesProductRepository;
    private final FoodProductRepository foodProductRepository;
    private final WishboxRepository wishboxRepository;

    // 상품코드 별 상품 엔티티
    public Class<?> searchEntityById(String productCode) {
        // 123 에서 1(대분류 코드) 추출
        char index = productCode.charAt(0);

        switch (index) {
            case '1':
                return DigitalProduct.class;
            case '2':
                return FurnitureProduct.class;
            case '3':
                return NecessariesProduct.class;
            case '4':
                return FoodProduct.class;
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
    public SearchContentDto searchProductById(String productCode, Long memberId) {
        // 123 에서 1(대분류 코드) 추출
        char index = productCode.charAt(0);

        ProductInterface product;
        switch (index) {
            case '1':
                product = digitalProductRepository.findByCode(productCode).orElse(null);
                break;
            case '2':
                product = furnitureProductRepository.findByCode(productCode).orElse(null);
                break;
            case '3':
                product = necessariesProductRepository.findByCode(productCode).orElse(null);
                break;
            case '4':
                product = foodProductRepository.findByCode(productCode).orElse(null);
                break;
            default:
                throw new BusinessException(ErrorCode.NOT_EXISTS_CATEGORY);

        }
        if (product != null) {
            Set<String> wishProductCode = wishboxRepository.findProductById(memberId);
            // 찜 여부
            boolean wishYn = (wishProductCode != null) && wishProductCode.contains(product.getCode());
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
                    .build();
        } else {
            throw new BusinessException(ErrorCode.NOT_EXISTS_PRODUCT);
        }
    }

}

