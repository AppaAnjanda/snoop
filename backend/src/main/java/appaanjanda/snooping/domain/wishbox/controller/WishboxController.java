package appaanjanda.snooping.domain.wishbox.controller;

import appaanjanda.snooping.domain.member.service.dto.UserResponse;
import appaanjanda.snooping.domain.wishbox.service.WishboxService;
import appaanjanda.snooping.global.response.Response;
import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/add/{productId}")
    public ResponseEntity<Object> addWishbox(@MemberInfo MembersInfo membersInfo, @PathVariable String productId) {
        return ResponseEntity.ok(wishboxService.addWishbox(membersInfo.getId(), productId));
    }


}
