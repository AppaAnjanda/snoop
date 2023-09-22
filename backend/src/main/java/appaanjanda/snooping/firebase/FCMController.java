// package appaanjanda.snooping.firebase;
//
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import lombok.RequiredArgsConstructor;
//
// @RestController
// @RequiredArgsConstructor
// @RequestMapping("/v1/fcm")
// public class FCMController {
//
//     private final FCMNotificationService fcmNotificationService;
//
//     @PostMapping()
//     public String sendMessaging(@RequestBody FCMNotificationRequestDto requestDto){
//         return fcmNotificationService.sendNotificationByToken(requestDto);
//     }
//
// }
