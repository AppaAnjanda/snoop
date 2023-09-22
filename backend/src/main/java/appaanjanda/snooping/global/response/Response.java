package appaanjanda.snooping.global.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL) //  null 값을 가지는 필드는, JSON 응답에 포함되지 않음
@Getter
@AllArgsConstructor
public class Response<T> {

	private HttpStatus status;
	private String message;
	private T data;
}
