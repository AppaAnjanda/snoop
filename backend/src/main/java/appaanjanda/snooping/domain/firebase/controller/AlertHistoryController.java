package appaanjanda.snooping.domain.firebase.controller;

import appaanjanda.snooping.domain.firebase.service.AlertHistoryService;
import appaanjanda.snooping.jwt.MemberInfo;
import appaanjanda.snooping.jwt.MembersInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alert")
@RequiredArgsConstructor
public class AlertHistoryController {
    private final AlertHistoryService alertHistoryService;

    @GetMapping("/history")
    public ResponseEntity<Object> getAlertHistory(@MemberInfo MembersInfo membersInfo) {
        return ResponseEntity.ok(alertHistoryService.getAlertHistory(membersInfo.getId()));
    }
}
