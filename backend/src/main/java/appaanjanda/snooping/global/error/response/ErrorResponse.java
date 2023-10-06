package appaanjanda.snooping.global.error.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorResponse {
    private int httpStatus;
    private String errorCode;
    private String errorMessage;

    public static ErrorResponse of(HttpStatus httpStatus, String errorCode, String errorMessage) {
        return ErrorResponse.builder()
                .httpStatus(httpStatus.value())
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
    }
}