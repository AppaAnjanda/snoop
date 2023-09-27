package appaanjanda.snooping.domain.firebase;

import appaanjanda.snooping.domain.firebase.entity.AlertHistory;
import appaanjanda.snooping.domain.firebase.repository.AlertHistoryRepository;
import appaanjanda.snooping.global.error.code.ErrorCode;
import appaanjanda.snooping.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import appaanjanda.snooping.domain.member.entity.Member;
import appaanjanda.snooping.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class FCMNotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final MemberRepository memberRepository;
    private final AlertHistoryRepository alertHistoryRepository;

    public String sendNotification(FCMNotificationRequestDto requestDto) {

        Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(()
                -> new BusinessException(ErrorCode.NOT_EXISTS_USER_ID)
        );

        if (member.getFirebaseToken() != null) {
            Notification notification = Notification.builder()
                    .setTitle(requestDto.getTitle())
                    .setBody(requestDto.getBody())
                    .build();

            Message message = Message.builder()
                    .setToken(member.getFirebaseToken())
                    .setNotification(notification)
                    .build();

            try {
                firebaseMessaging.send(message);

                // 알림 내역 저장
                AlertHistory alertHistory = AlertHistory.builder()
                        .title(requestDto.getTitle())
                        .body(requestDto.getBody())
                        .member(member).build();
                alertHistoryRepository.save(alertHistory);

                return "send 성공";

            } catch (FirebaseMessagingException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            return "FCMToken 없습니다";
        }
    }
}
