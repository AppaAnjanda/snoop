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
import appaanjanda.snooping.domain.product.service.ProductSearchService;
import appaanjanda.snooping.domain.search.dto.SearchContentDto;
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
	private final ProductSearchService productSearchService;

	//찜 상품 등록
	public AddWishboxResponseDto addWishbox(Long memberId, String productCode, AddWishboxRequestDto addWishboxRequestDto) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_USER_ID));
		SearchContentDto searchContentDto = productSearchService.searchProductById(productCode, memberId);
		List<Wishbox> wishboxes = wishboxRepository.findByMember(member);

		Wishbox wishbox = Wishbox.builder()
				.alertPrice(addWishboxRequestDto.getAlertPrice())
				.alertYn(true)
				.productCode(productCode)
				.member(member)
				.provider(searchContentDto.getProvider())
				.build();

		boolean exists = wishboxes.stream().anyMatch(existingWishbox -> existingWishbox.getProductCode().equals(wishbox.getProductCode()));
		if (exists) {
			throw new BusinessException(ErrorCode.ALREADY_REGISTERED_WISHBOX);
		}

		wishboxRepository.saveAndFlush(wishbox);

		return AddWishboxResponseDto.builder()
				.wishboxId(wishbox.getId())
				.productCode(productCode)
				.alertYn(true)
				.alertPrice(addWishboxRequestDto.getAlertPrice())
				.provider(searchContentDto.getProvider())
				.build();
	}

	// 찜 상품 목록 조회
	@Transactional(readOnly = true)
	public List<WishboxResponseDto> getWishboxList(Long memberId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_USER_ID));
		List<Wishbox> wishboxes = wishboxRepository.findByMember(member);

		List<WishboxResponseDto> wishboxResponseDtoList = new ArrayList<>();
		for (Wishbox wishbox : wishboxes) {
			SearchContentDto searchContentDto = productSearchService.searchProductById(wishbox.getProductCode(), memberId);
			WishboxResponseDto wishboxResponseDto = WishboxResponseDto.builder()
					.wishboxId(wishbox.getId())
					.productCode(wishbox.getProductCode())
					.productName(searchContentDto.getProductName())
					.productImage(searchContentDto.getProductImage())
					.price(searchContentDto.getPrice())
					.alertPrice(wishbox.getAlertPrice())
					.alertYn(wishbox.getAlertYn())
					.build();
			wishboxResponseDtoList.add(wishboxResponseDto);
		}
		return wishboxResponseDtoList;
	}

	// 찜 상품 삭제
	public RemoveWishboxResponseDto removeWishbox(Long wishboxId) {
		Wishbox wishbox = wishboxRepository.findById(wishboxId)
				.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_WISHBOX_ID));
		wishboxRepository.delete(wishbox);

		return RemoveWishboxResponseDto
				.builder()
				.removeId(wishboxId)
				.build();
	}

	// 찜 상품 알림 가격 변경
	public WishboxResponseDto updateAlertPrice(Long memberId, Long wishboxId, UpdateAlertPriceRequestDto updateAlertPriceRequestDto) {
		Wishbox wishbox = wishboxRepository.findById(wishboxId)
				.orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_WISHBOX_ID));
		SearchContentDto searchContentDto = productSearchService.searchProductById(wishbox.getProductCode(), memberId);

		// 가격 변경
		wishbox.updateAlertPrice(updateAlertPriceRequestDto.getAlertPrice());

		return WishboxResponseDto.builder()
				.wishboxId(wishboxId)
				.alertPrice(wishbox.getAlertPrice())
				.alertYn(wishbox.getAlertYn())
				.productCode(wishbox.getProductCode())
				.productName(searchContentDto.getProductName())
				.productImage(searchContentDto.getProductImage())
				.price(searchContentDto.getPrice())
				.build();
	}

}
