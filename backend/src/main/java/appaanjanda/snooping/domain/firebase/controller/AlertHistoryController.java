package appaanjanda.snooping.domain.firebase.controller;

import appaanjanda.snooping.domain.firebase.dto.AlertHistoryDto;
import appaanjanda.snooping.domain.firebase.service.AlertHistoryService;
import appaanjanda.snooping.domain.member.service.dto.UserResponse;
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
@RequestMapping("/api/alert")
@RequiredArgsConstructor
public class AlertHistoryController {
    private final AlertHistoryService alertHistoryService;

    // 알림 목록 조회
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "요청 오류 "),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "알림 목록 조회", description = "알림의 목록을 전체 조회", tags = { "AlertHistory Controller" })
    @GetMapping("/history")
    public ResponseEntity<List<AlertHistoryDto>> getAlertHistory(@MemberInfo MembersInfo membersInfo) {
        return ResponseEntity.ok(alertHistoryService.getAlertHistory(membersInfo.getId()));
    }

    // 알림 단일 삭제
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "알림 기록 삭제", description = "알림 삭제", tags = { "AlertHistory Controller" })
    @DeleteMapping("/history/{alertId}")
    public ResponseEntity<String> deleteAlertHistory(@MemberInfo MembersInfo membersInfo, @PathVariable Long alertId) {
        alertHistoryService.deleteAlertHistory(alertId, membersInfo.getId());
        return ResponseEntity.ok(String.format("%d번 알림 삭제", alertId));
    }

    // 알림 전체 삭제
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "알림 전체 삭제", description = "알림 전체 삭제", tags = { "AlertHistory Controller" })
    @DeleteMapping("/history/all")
    public ResponseEntity<String> deleteAlertHistory(@MemberInfo MembersInfo membersInfo) {
        alertHistoryService.deleteAllAlertHistory(membersInfo.getId());
        return ResponseEntity.ok("알림 전체 삭제");
    }
}
