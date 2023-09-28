package appaanjanda.snooping.domain.firebase.service;

import appaanjanda.snooping.domain.firebase.dto.AlertHistoryDto;
import appaanjanda.snooping.domain.firebase.entity.AlertHistory;
import appaanjanda.snooping.domain.firebase.repository.AlertHistoryRepository;
import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.domain.member.repository.MemberRepository;
import appaanjanda.snooping.global.error.code.ErrorCode;
import appaanjanda.snooping.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertHistoryService {
    private final AlertHistoryRepository alertHistoryRepository;
    private final MemberRepository memberRepository;

    // 알림 목록 조회
    public List<AlertHistoryDto> getAlertHistory(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXISTS_USER_ID));
        List<AlertHistory> alertHistories = alertHistoryRepository.findAllByMember(member);

        List<AlertHistoryDto> alertHistoryDtos = new ArrayList<>();
        for (AlertHistory alertHistory : alertHistories) {
            AlertHistoryDto alertHistoryDto = AlertHistoryDto.builder()
                    .title(alertHistory.getTitle())
                    .body(alertHistory.getBody())
                    .build();
            alertHistoryDtos.add(alertHistoryDto);
        }

        return alertHistoryDtos;
    }
}
