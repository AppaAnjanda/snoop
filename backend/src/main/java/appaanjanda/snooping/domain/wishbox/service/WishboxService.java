package appaanjanda.snooping.domain.wishbox.service;

import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.domain.member.repository.MemberRepository;
import appaanjanda.snooping.domain.product.entity.product.DigitalProduct;
import appaanjanda.snooping.domain.product.entity.product.FoodProduct;
import appaanjanda.snooping.domain.product.entity.product.FurnitureProduct;
import appaanjanda.snooping.domain.product.entity.product.NecessariesProduct;
import appaanjanda.snooping.domain.product.repository.DigitalProductRepository;
import appaanjanda.snooping.domain.product.repository.FoodProductRepository;
import appaanjanda.snooping.domain.product.repository.FurnitureProductRepository;
import appaanjanda.snooping.domain.product.repository.NecessariesProductRepository;
import appaanjanda.snooping.domain.wishbox.entity.Wishbox;
import appaanjanda.snooping.domain.wishbox.service.dto.*;
import appaanjanda.snooping.global.error.code.ErrorCode;
import appaanjanda.snooping.global.error.exception.BadRequestException;
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
	private final DigitalProductRepository digitalProductRepository;
	private final FurnitureProductRepository furnitureProductRepository;
	private final NecessariesProductRepository necessariesProductRepository;
	private final FoodProductRepository foodProductRepository;
	private final WishboxRepository wishboxRepository;
	private final MemberRepository memberRepository;

	public void save(Long id, SaveItemRequest request){

	}

	//찜 상품 등록
	public AddWishboxResponseDto addWishbox(Long memberId, String productId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTS_USER_ID));

		searchProductById(productId);

		Wishbox wishbox = Wishbox.builder()
				.alertPrice(0)
				.alertYn(false)
				.productId(productId)
				.member(member)
				.build();
		wishboxRepository.save(wishbox);

		return AddWishboxResponseDto
				.builder()
				.productId(productId)
				.alertYn(false)
				.alertPrice(0)
				.build();
	}

	// 찜 상품 목록 조회
	// TODO : 추가적으로 Response DTO 명확하게 수정
	@Transactional(readOnly = true)
	public List<WishboxResponseDto> getWishboxList(Long memberId) {
		Member member = memberRepository.findById(memberId)
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
	}

	// 찜 상품 삭제
	public RemoveWishboxResponseDto removeWishbox(Long memberId, Long wishboxId) {
		Wishbox wishbox = wishboxRepository.findById(wishboxId)
				.orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXISTS_WISHBOX_ID));
		wishboxRepository.delete(wishbox);

		return RemoveWishboxResponseDto
				.builder()
				.removeId(wishboxId)
				.build();
	}

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

}
