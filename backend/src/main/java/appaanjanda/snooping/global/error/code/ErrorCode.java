package appaanjanda.snooping.global.error.code;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

    TEST(HttpStatus.INTERNAL_SERVER_ERROR, "001", "exception test"),
    NOT_EXISTS_DATA(HttpStatus.NOT_FOUND, "002", "존재하지 않는 데이터입니다."),
    NOT_VALID_DATA(HttpStatus.BAD_REQUEST, "003", "유효하지 않는 데이터입니다."),
    INVALID_DATA_TYPE(HttpStatus.BAD_REQUEST, "004", "잘못된 데이터 타입입니다."),
    ALREADY_REGISTERED_DATA(HttpStatus.BAD_REQUEST, "005", "이미 존재하는 데이터입니다."),
//    FORBIDDEN_ROLE(HttpStatus.FORBIDDEN, "006", "해당 Role이 아닙니다."),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "007", "지원하지 않는 미디어 유형입니다."),

    // 인증 && 인가
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-001", "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "A-002", "해당 토큰은 유효한 토큰이 아닙니다."),
    NOT_EXISTS_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "A-003", "Authorization Header가 빈 값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE(HttpStatus.UNAUTHORIZED, "A-004", "인증 타입이 Bearer 타입이 아닙니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A-005", "해당 refresh token은 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "A-006", "해당 refresh token은 만료됐습니다."),
    NOT_ACCESS_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "A-007", "해당 토큰은 ACCESS TOKEN이 아닙니다."),
    FORBIDDEN_ROLE(HttpStatus.FORBIDDEN, "A-008", "해당 Role이 아닙니다."),

    // 유저
    NOT_EXISTS_USER_ID(HttpStatus.NOT_FOUND, "U-001", "존재하지 않는 유저 아이디입니다."),
    NOT_EXISTS_USER_NICKNAME(HttpStatus.NOT_FOUND, "U-002", "존재하지 않는 유저 닉네임입니다."),
    NOT_EXISTS_USER_EMAIL(HttpStatus.NOT_FOUND, "U-003", "존재하지 않는 유저 이메일입니다."),
    ALREADY_REGISTERED_USER_ID(HttpStatus.BAD_REQUEST, "U-004", "이미 존재하는 유저 아이디입니다."),
    NOT_EXISTS_USER_PASSWORD(HttpStatus.NOT_FOUND, "U-005", "존재하지 않는 유저 비밀번호입니다."),
    INVALID_USER_DATA(HttpStatus.BAD_REQUEST, "U-006", "잘못된 유저 정보입니다."),
    INVALID_ADMIN(HttpStatus.BAD_REQUEST, "U-007", "Admin은 제외 시켜주세요."),

    // 카드
    NOT_EXISTS_CARD_NAME(HttpStatus.NOT_FOUND, "C-001", "삭제 요청하신 카드가 존재하지 않습니다."),

    // 이미지
    FAIL_DELETE_FILE(HttpStatus.INTERNAL_SERVER_ERROR, "F-001", "이미지 파일 삭제에 실패"),

    // 상품
    NOT_EXISTS_DIGITAL_PRODUCT(HttpStatus.NOT_FOUND, "P-001", "디지털가전 상품이 존재하지 않습니다."),
    NOT_EXISTS_FURNITURE_PRODUCT(HttpStatus.NOT_FOUND, "P-002", "가구 상품이 존재하지 않습니다."),
    NOT_EXISTS_NECESSARIES_PRODUCT(HttpStatus.NOT_FOUND, "P-003", "생활용품 상품이 존재하지 않습니다."),
    NOT_EXISTS_FOOD_PRODUCT(HttpStatus.NOT_FOUND, "P-004", "식품 상품이 존재하지 않습니다."),
    NOT_EXISTS_PRODUCT(HttpStatus.NOT_FOUND, "P-005", "상품이 존재하지 않습니다."),
    NOT_EXISTS_CATEGORY(HttpStatus.NOT_FOUND, "P-006", "상품 카테고리가 존재하지 않습니다."),

    // 찜 상품
    NOT_EXISTS_WISHBOX_ID(HttpStatus.NOT_FOUND, "W-005", "찜 상품이 존재하지 않습니다."),

    // 검색
    ELASTICSEARCH_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "S-001", "Elasticsearch 검색 실패"),
    NOT_EXISTS_RESULT(HttpStatus.NOT_FOUND, "S-002", "검색 결과가 존재하지 않습니다."),
    INVALID_PRICE(HttpStatus.BAD_REQUEST, "S-003", "유효하지 않은 가격범위 입니다."),
    NOT_EXISTS_KEYWORD(HttpStatus.NOT_FOUND, "S-004", "해당 검색기록이 존재하지 않습니다.");


    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}
