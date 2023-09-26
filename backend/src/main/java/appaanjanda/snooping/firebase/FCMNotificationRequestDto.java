 package appaanjanda.snooping.firebase;

 import lombok.AllArgsConstructor;
 import lombok.Builder;
 import lombok.Getter;
 import lombok.NoArgsConstructor;

 @Getter
 @NoArgsConstructor
 @AllArgsConstructor
 @Builder
 public class FCMNotificationRequestDto {

     private Long memberId;
     private String title;
     private String body;

 }
