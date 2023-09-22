// package appaanjanda.snooping.firebase;
//
// import org.springframework.stereotype.Service;
//
// import com.google.firebase.messaging.FirebaseMessaging;
// import com.google.firebase.messaging.FirebaseMessagingException;
// import com.google.firebase.messaging.Message;
// import com.google.firebase.messaging.Notification;
//
// import appaanjanda.snooping.domain.member.entity.Member;
// import appaanjanda.snooping.domain.member.repository.MemberRepository;
// import lombok.RequiredArgsConstructor;
//
//
// @RequiredArgsConstructor
// @Service
// public class FCMNotificationService {
//
//     private final FirebaseMessaging firebaseMessaging;
//     private final MemberRepository memberRepository;
//
//     public String sendNotificationByToken(FCMNotificationRequestDto requestDto) {
//
//         Member users = memberRepository.findById(requestDto.getTargetUserId()).orElseThrow();
//
//         if (users.getFirebaseToken() != null) {
//             Notification notification = Notification.builder()
//                     .setTitle(requestDto.getTitle())
//                     .setBody(requestDto.getBody())
//                     .build();
//
//             Message message = Message.builder()
//                     .setToken(users.getFirebaseToken())
//                     .setNotification(notification)
//                     .build();
//
//             try {
//                 firebaseMessaging.send(message);
//             } catch (FirebaseMessagingException e) {
//                 throw new RuntimeException(e);
//             }
//         } else {
//             return "Token 없머";
//         }
//         return "뭐";
//     }
// }
