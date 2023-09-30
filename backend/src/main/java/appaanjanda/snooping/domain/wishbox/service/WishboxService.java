package appaanjanda.snooping.domain.wishbox.service;

import appaanjanda.snooping.domain.firebase.FCMNotificationRequestDto;
import appaanjanda.snooping.domain.firebase.FCMNotificationService;
import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.domain.member.repository.MemberRepository;
import appaanjanda.snooping.domain.product.service.ProductSearchService;
import appaanjanda.snooping.domain.search.dto.SearchContentDto;
import appaanjanda.snooping.domain.wishbox.entity.Wishbox;
import appaanjanda.snooping.domain.wishbox.service.dto.AddAlertResponseDto;
import appaanjanda.snooping.domain.wishbox.service.dto.RemoveWishboxResponseDto;
import appaanjanda.snooping.external.fastApi.CoupangCrawlingCaller;
import appaanjanda.snooping.external.fastApi.NaverApiCaller;
import appaanjanda.snooping.domain.wishbox.service.dto.*;
import appaanjanda.snooping.global.error.code.ErrorCode;
import appaanjanda.snooping.global.error.exception.BusinessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import appaanjanda.snooping.domain.wishbox.repository.WishboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WishboxService {

    private final WishboxRepository wishboxRepository;
    private final MemberRepository memberRepository;
    private final CoupangCrawlingCaller coupangCrawlingCaller;
    private final NaverApiCaller naverApiCaller;
    private final ProductSearchService productSearchService;
    private final FCMNotificationService fcmNotificationService;

    //찜 상품 등록
    public AddAlertResponseDto addAlert(Long memberId, String productCode, AddAlertRequestDto addAlertRequestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_USER_ID));
        SearchContentDto searchContentDto = productSearchService.searchProductById(productCode, memberId);

        Wishbox wishbox;
        // 찜이 없는 경우 -> 찜 목록에 등록 및 알림가격 설정
        if (!searchContentDto.isWishYn()) {
            wishbox = Wishbox.builder()
                    .alertPrice(addAlertRequestDto.getAlertPrice())
                    .alertYn(true)
                    .productCode(productCode)
                    .member(member)
                    .provider(searchContentDto.getProvider())
                    .build();
            wishboxRepository.saveAndFlush(wishbox);
        } else {
            wishbox = wishboxRepository.findByProductCodeAndMember(productCode, member)
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_PRODUCT));
            // 찜이 있고 기존 알림도 있는 경우 -> 알림 취소
            if (wishbox.getAlertYn()) {
                wishbox.updateAlertPrice(0);
            }
            // 찜이 있고 기존 알림은 없는 경우 -> 알림 설정
            else {
                wishbox.updateAlertPrice(addAlertRequestDto.getAlertPrice());
            }
        }

        return AddAlertResponseDto.builder()
                .wishboxId(wishbox.getId())
                .productCode(productCode)
                .alertYn(wishbox.getAlertYn())
                .alertPrice(wishbox.getAlertPrice())
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

    // 찜 상품 기져와서 업데이트
//	@Scheduled(cron = "0 */10 * * * *")
//	public void wishboxUpdate() {
//		List<Wishbox> allWishbox = wishboxRepository.findAll();
//
//		for (Wishbox wishbox : allWishbox) {
//			String productCode = wishbox.getProductCode();
//			if (wishbox.getProvider().equals("쿠팡")){ continue;
////				coupangCrawlingCaller.oneProductSearch(productCode);
//
//			} else {
//				naverApiCaller.oneProductSearch(productCode);
//			}
//		}
//	}

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

    // 찜 상품 토글
    public AddWishboxResponseDto addWishbox(Long memberId, String productCode) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_USER_ID));
        SearchContentDto searchContentDto = productSearchService.searchProductById(productCode, memberId);

        boolean wishYn = false;
        if (!searchContentDto.isWishYn()) {
            Wishbox wishbox = Wishbox.builder()
                    .alertPrice(0)
                    .alertYn(false)
                    .productCode(productCode)
                    .member(member)
                    .provider(searchContentDto.getProvider())
                    .build();

            wishboxRepository.save(wishbox);
            wishYn = true;
        } else {
            Wishbox wishbox = wishboxRepository.findByProductCodeAndMember(productCode, member)
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_PRODUCT));
            wishboxRepository.delete(wishbox);
        }

        return AddWishboxResponseDto.builder()
                .wishYn(wishYn)
                .build();
    }

    // 현재멤버의 찜 상품인지 체크
    public boolean checkMemberWishbox(String productCode, Long memberId) {

        boolean wishYn = false;

        if (memberId != null) {
            // 현재 멤버 찜 목록
            Set<String> wishProductCode = wishboxRepository.findProductById(memberId);
            // 찜 여부
            wishYn = (wishProductCode != null) && wishProductCode.contains(productCode);
        }
        return wishYn;
    }

    // 찜 상품인지 체크
    public boolean checkWishbox(String productCode) {

        Set<String> wishboxProductCode = wishboxRepository.findAllProductCode();

        if (wishboxProductCode.contains(productCode)) return true;
        else return false;
    }

    // 알림 가격 체크
    public void checkAlertPrice(String productCode, int price) {

        List<Wishbox> wishboxList = wishboxRepository.findWishboxByProductCode(productCode);

        for (Wishbox wishbox : wishboxList) {
            log.info("알림가격체크 {}", price);
			if (wishbox.getAlertYn() && wishbox.getAlertPrice() >= price) {
				sendAlert(wishbox, price);
			}
        }
    }

	// 알림보내기
	public void sendAlert(Wishbox wishbox, int price) {

		int length = wishbox.getProductCode().length();
		String productName = wishbox.getProductCode().substring(2, length);

        log.info("메시지 전송");
		FCMNotificationRequestDto requestDto = FCMNotificationRequestDto.builder()
				.memberId(wishbox.getMember().getId())
				.title("지정가 알림")
				.body(productName + "의 가격이 " + price + "에 도달했습니다 !")
				.build();

		fcmNotificationService.sendNotification(requestDto);

	}
}
