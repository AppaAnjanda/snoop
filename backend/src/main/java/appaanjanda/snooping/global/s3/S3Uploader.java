// package appaanjanda.snooping.global.s3;
//
// import java.awt.image.BufferedImage;
// import java.io.File;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.util.Optional;
// import java.util.UUID;
//
// import javax.imageio.ImageIO;
//
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;
// import org.springframework.web.multipart.MultipartFile;
//
// import com.amazonaws.SdkClientException;
// import com.amazonaws.services.s3.AmazonS3Client;
// import com.amazonaws.services.s3.model.CannedAccessControlList;
// import com.amazonaws.services.s3.model.PutObjectRequest;
//
// import appaanjanda.snooping.global.error.code.ErrorCode;
// import appaanjanda.snooping.global.error.exception.BadRequestException;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Component
// @Slf4j
// @RequiredArgsConstructor
// public class S3Uploader {
//     private final AmazonS3Client amazonS3Client;
//     private static final String EXTENSION = "png";
//     @Value("${cloud.aws.s3.bucket}")
//     private String bucket;
//     @Value("d3byqpylj7jy1e.cloudfront.net")
//     private String cloudFrontUrl;
//
//     public String uploadFiles(MultipartFile multipartFile, String dirName) throws IOException {
//         File uploadFile = convert(multipartFile) // 파일 변환할 수 없으면 에러
//                 .orElseThrow(() -> { return new IllegalArgumentException("error: MultipartFile -> File convert fail");});
//         return upload(uploadFile, dirName);
//     }
//
//     // S3서버에 올라간 파일을 지우기
//     public boolean deleteFiles(String fileName) throws IOException{
//         try{
//             amazonS3Client.deleteObject(bucket, fileName);
//             log.info("삭제완료!!");
//         } catch (SdkClientException e) {
//             log.info("e : {}", e);
//             throw new BadRequestException(ErrorCode.FAIL_DELETE_FILE);
//         }
//         return true;
//     }
//
//     public String upload(File uploadFile, String filePath) {
//         String fileName = filePath + "/" + UUID.randomUUID() + "."+ uploadFile.getName();   // S3에 저장된 파일 이름
//         log.info("upload");
//         String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
//         log.info("upload URL : {}", uploadImageUrl);
//         removeNewFile(uploadFile);
//         return uploadImageUrl;
//     }
//
//     // S3로 업로드
//     private String putS3(File uploadFile, String fileName) {
//         amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
//             CannedAccessControlList.PublicRead));
//         log.info("upload putS3");
//         //return amazonS3Client.getUrl(bucket, fileName).toString(); // S3 업로드된 주소 반환
//         return "https://" + cloudFrontUrl + "/" + fileName; // S3 업로드된 주소 반환
//         /**
//         * return을 할때 crit service s3 주소기 때문에 fileName만 반환진행 -> 반환한 후에는 CloundFront URI 붙여서 DB에 저장
//          */
//     }
//
//     // 로컬에 저장된 이미지 지우기
//     private void removeNewFile(File targetFile) {
//         if (targetFile.delete()) {
//             System.out.println("File delete success");
//             return;
//         }
//         System.out.println("File delete fail");
//     }
//
//     // 로컬에 파일 업로드 하기
//     private Optional<File> convert(MultipartFile file) throws IOException {
//         File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
//         if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
//             try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
//                 fos.write(file.getBytes());
//             }
//             return Optional.of(convertFile);
//         }
//         return Optional.empty();
//     }
//
//     // Convert MultipartFile to File
//     private File convertMultipartFileToFile(MultipartFile file) throws IOException {
//         File convFile = new File(file.getOriginalFilename());
//         convFile.createNewFile();
//         FileOutputStream fos = new FileOutputStream(convFile);
//         fos.write(file.getBytes());
//         fos.close();
//         return convFile;
//     }
//
// }
