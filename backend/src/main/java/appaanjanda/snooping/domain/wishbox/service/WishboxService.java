package appaanjanda.snooping.domain.wishbox.service;

import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.domain.member.repository.MemberRepository;
import appaanjanda.snooping.domain.product.entity.product.DigitalProduct;
import appaanjanda.snooping.domain.product.entity.product.FoodProduct;
import appaanjanda.snooping.domain.product.entity.product.FurnitureProduct;
import appaanjanda.snooping.domain.product.entity.product.NecessariesProduct;
import appaanjanda.snooping.domain.product.repository.product.DigitalProductRepository;
import appaanjanda.snooping.domain.product.repository.product.FoodProductRepository;
import appaanjanda.snooping.domain.product.repository.product.FurnitureProductRepository;
import appaanjanda.snooping.domain.product.repository.product.NecessariesProductRepository;
import appaanjanda.snooping.domain.wishbox.entity.Wishbox;
import appaanjanda.snooping.domain.wishbox.service.dto.*;
import appaanjanda.snooping.global.error.code.ErrorCode;
import appaanjanda.snooping.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import appaanjanda.snooping.domain.wishbox.repository.WishboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WishboxService {

	private final WishboxRepository wishboxRepository;
	private final MemberRepository memberRepository;


	//찜 상품 등록
<<<<<<< HEAD
	public AddWishboxResponseDto addWishbox(Long memberId, String productId, AddWishboxRequestDto addWishboxRequestDto) {
=======
	public AddWishboxResponseDto addWishbox(Long memberId, String productCode) {
>>>>>>> 2c3958abe891aaacec106801c9ad2226a7a5d8d5
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_USER_ID));

		searchProductById(productId);

		Wishbox wishbox = Wishbox.builder()
<<<<<<< HEAD
				.alertPrice(addWishboxRequestDto.getAlertPrice())
				.alertYn(true)
				.productId(productId)
=======
				.alertPrice(0)
				.alertYn(false)
				.productCode(productCode)
>>>>>>> 2c3958abe891aaacec106801c9ad2226a7a5d8d5
				.member(member)
				.build();
		wishboxRepository.save(wishbox);

		return AddWishboxResponseDto
				.builder()
<<<<<<< HEAD
				.productId(productId)
				.alertYn(true)
				.alertPrice(addWishboxRequestDto.getAlertPrice())
=======
				.productCode(productCode)
				.alertYn(false)
				.alertPrice(0)
>>>>>>> 2c3958abe891aaacec106801c9ad2226a7a5d8d5
				.build();
	}

	// 찜 상품 목록 조회
	// TODO : 추가적으로 Response DTO 명확하게 수정
	@Transactional(readOnly = true)
	public List<WishboxResponseDto> getWishboxList(Long memberId) {
		Member member = memberRepository.findById(memberId)
<<<<<<< HEAD
				.orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID));

		List<Wishbox> wishboxList = member.getWishboxList();

		List<WishboxResponseDto> wishboxResponseDtoList = new ArrayList<>();
		for (Wishbox wishbox : wishboxList) {
			String productId = wishbox.getProductId();
			Object product = searchProductById(productId);
			WishboxResponseDto wishboxResponseDto = getObjectToDto(productId, product);
			wishboxResponseDtoList.add(wishboxResponseDto);
		}

		return wishboxResponseDtoList;
=======
				.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_USER_ID));
		return member.getWishboxList();
>>>>>>> 2c3958abe891aaacec106801c9ad2226a7a5d8d5
	}

	// 찜 상품 삭제
	public RemoveWishboxResponseDto removeWishbox(Long memberId, Long wishboxId) {
		Wishbox wishbox = wishboxRepository.findById(wishboxId)
				.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_WISHBOX_ID));
		wishboxRepository.delete(wishbox);

		return RemoveWishboxResponseDto
				.builder()
				.removeId(wishboxId)
				.build();
	}
<<<<<<< HEAD

	// 상품id로 조회
	public Object searchProductById(String productId) {
		// product_123 에서 1(대분류 코드) 추출
		char index = productId.split("_")[1].charAt(0);

		switch (index) {
			case '1':
				return digitalProductRepository.findById(productId)
						.orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTS_DIGITAL_PRODUCT));
			case '2':
				return furnitureProductRepository.findById(productId)
						.orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTS_NECESSARIES_PRODUCT));
			case '3':
				return necessariesProductRepository.findById(productId)
						.orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTS_NECESSARIES_PRODUCT));
			case '4':
				return foodProductRepository.findById(productId)
						.orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTS_FOOD_PRODUCT));
			default:
				throw new BadRequestException(ErrorCode.NOT_EXISTS_PRODUCT);
		}
	}

	public WishboxResponseDto getObjectToDto(String productId, Object product) {
		char index = productId.split("_")[1].charAt(0);

		switch (index) {
			case '1':
				DigitalProduct digitalProduct = (DigitalProduct) product;
				return WishboxResponseDto.builder()
						.productId(productId)
						.productName(digitalProduct.getProductName())
						.productImage(digitalProduct.getProductImage())
						.price(digitalProduct.getPrice())
						.build();
			case '2':
				FurnitureProduct furnitureProduct = (FurnitureProduct) product;
				return WishboxResponseDto.builder()
						.productId(productId)
						.productName(furnitureProduct.getProductName())
						.productImage(furnitureProduct.getProductImage())
						.price(furnitureProduct.getPrice())
						.build();
			case '3':
				NecessariesProduct necessariesProduct = (NecessariesProduct) product;
				return WishboxResponseDto.builder()
						.productId(productId)
						.productName(necessariesProduct.getProductName())
						.productImage(necessariesProduct.getProductImage())
						.price(necessariesProduct.getPrice())
						.build();
			case '4':
				FoodProduct foodProduct = (FoodProduct) product;
				return WishboxResponseDto.builder()
						.productId(productId)
						.productName(foodProduct.getProductName())
						.productImage(foodProduct.getProductImage())
						.price(foodProduct.getPrice())
						.build();
			default:
				throw new BadRequestException(ErrorCode.NOT_EXISTS_PRODUCT);
		}
	}

=======
>>>>>>> 2c3958abe891aaacec106801c9ad2226a7a5d8d5
}
