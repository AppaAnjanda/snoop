package appaanjanda.snooping.domain.wishbox.service;

import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.domain.member.repository.MemberRepository;
import appaanjanda.snooping.domain.product.repository.product.DigitalProductRepository;
import appaanjanda.snooping.domain.product.repository.product.FoodProductRepository;
import appaanjanda.snooping.domain.product.repository.product.FurnitureProductRepository;
import appaanjanda.snooping.domain.product.repository.product.NecessariesProductRepository;
import appaanjanda.snooping.domain.wishbox.entity.Wishbox;
import appaanjanda.snooping.domain.wishbox.service.dto.AddWishboxResponseDto;
import appaanjanda.snooping.domain.wishbox.service.dto.RemoveWishboxResponseDto;
import appaanjanda.snooping.global.error.code.ErrorCode;
import appaanjanda.snooping.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import appaanjanda.snooping.domain.wishbox.repository.WishboxRepository;
import appaanjanda.snooping.domain.wishbox.service.dto.SaveItemRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
				.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_USER_ID));

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
	public List<Wishbox> getWishboxList(Long memberId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_USER_ID));
		return member.getWishboxList();
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

	// 상품id로 조회
	public Object searchProductById(String productId) {
		// product_123 에서 1(대분류 코드) 추출
		char index = productId.split("_")[1].charAt(0);

		switch (index) {
			case '1':
				return digitalProductRepository.findById(productId)
						.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_DIGITAL_PRODUCT));
			case '2':
				return furnitureProductRepository.findById(productId)
						.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_NECESSARIES_PRODUCT));
			case '3':
				return necessariesProductRepository.findById(productId)
						.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_NECESSARIES_PRODUCT));
			case '4':
				return foodProductRepository.findById(productId)
						.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_FOOD_PRODUCT));
			default:
				throw new BusinessException(ErrorCode.NOT_EXISTS_PRODUCT);
		}
	}

}
