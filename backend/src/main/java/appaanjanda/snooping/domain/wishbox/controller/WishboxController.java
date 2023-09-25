package appaanjanda.snooping.domain.wishbox.controller;

import appaanjanda.snooping.domain.member.service.dto.UserResponse;
import appaanjanda.snooping.domain.wishbox.entity.Wishbox;
import appaanjanda.snooping.domain.wishbox.service.WishboxService;
import appaanjanda.snooping.domain.wishbox.service.dto.*;
import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishbox")
@RequiredArgsConstructor
public class WishboxController {
    private final WishboxService wishboxService;

    // 찜 상품 등록
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "요청 오류 "),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "찜 상품 등록", description = "상세 상품 화면에서 찜 버튼을 통해 상품id로 찜 상품 등록", tags = { "Wishbox Controller" })
    @PostMapping("/add/{productCode}")
    public ResponseEntity<AddWishboxResponseDto> addWishbox(@MemberInfo MembersInfo membersInfo, @PathVariable String productCode, @RequestBody AddWishboxRequestDto addWishboxRequestDto) {
        return ResponseEntity.ok(wishboxService.addWishbox(membersInfo.getId(), productCode, addWishboxRequestDto));
    }

    // 찜 상품 목록 조회
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "요청 오류 "),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "찜 상품 목록 조회", description = "찜 페이지 이동 시 진행되는 찜 목록 조회", tags = { "Wishbox Controller" })
    @GetMapping("")
    public ResponseEntity<List<WishboxResponseDto>> getWishboxList(@MemberInfo MembersInfo membersInfo) {
        return ResponseEntity.ok(wishboxService.getWishboxList(membersInfo.getId()));
    }

    // 찜 상품 삭제
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "요청 오류 "),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "찜 상품 삭제", description = "찜 페이지에서 선택항목에 대한 찜 상품 삭제", tags = { "Wishbox Controller" })
    @DeleteMapping("/remove/{wishboxId}")
    public ResponseEntity<RemoveWishboxResponseDto> removeWishbox(@MemberInfo MembersInfo membersInfo, @PathVariable Long wishboxId) {
        return ResponseEntity.ok(wishboxService.removeWishbox(wishboxId));
    }

    // 찜 상품 알림 가격 변경
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "요청 오류 "),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "찜 상품 알림 가격 변경", description = "찜 페이지에서 선택항목에 대한 알림 가격 변경", tags = { "Wishbox Controller" })
    @PostMapping("/update/{wishboxId}")
    public ResponseEntity<WishboxResponseDto> updateAlertPrice(@MemberInfo MembersInfo membersInfo, @PathVariable Long wishboxId, @RequestBody UpdateAlertPriceRequestDto updateAlertPriceRequestDto) {
        return ResponseEntity.ok(wishboxService.updateAlertPrice(membersInfo.getId(), wishboxId, updateAlertPriceRequestDto));
    }
}
