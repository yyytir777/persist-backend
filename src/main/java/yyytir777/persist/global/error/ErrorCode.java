package yyytir777.persist.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    TEST(HttpStatus.INTERNAL_SERVER_ERROR, "E001", "Business Error"),

    // Member
    MEMBER_NOT_EXIST(HttpStatus.BAD_REQUEST, "M001", "존재하지 않는 회원입니다."),
    MEMBER_EXIST(HttpStatus.BAD_REQUEST, "M002", "이미 존재하는 회원입니다."),
    NOT_MY_MEMBER(HttpStatus.BAD_REQUEST, "M003", "본인이 아닙니다."),

    // Log
    LOG_NOT_EXIST(HttpStatus.BAD_REQUEST, "L001", "존재하지 않는 로그입니다."),
    NOT_MY_LOG(HttpStatus.BAD_REQUEST, "L002", "본인의 로그가 아닙니다."),

    // Token
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "T001", "JWT토큰이 유효하지 않습니다."),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "T002", "accessToken이 만료되었습니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "T003", "지원하지 않는 JWT토큰 입니다."),
    JWT_CLAIMS_EMPTY(HttpStatus.UNAUTHORIZED, "T004", "JWT Claims가 비어있습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "T005", "refreshToken이 만료되었습니다."),
    HEADER_IS_NULL(HttpStatus.UNAUTHORIZED, "T006", "헤더가 빈 값입니다."),
    AUTH_NOT_FOUND(HttpStatus.UNAUTHORIZED, "T007", "인증 정보를 찾을 수 없습니다."),
    NEED_TO_RE_LOGIN(HttpStatus.UNAUTHORIZED, "T008", "서버의 refresh토큰과 일치하지 않습니다."),

    // Category
    CATEGORY_NOT_EXIST(HttpStatus.BAD_REQUEST, "C001", "해당 카테고리가 존재하지 않습니다."),
    CATEGORY_EXIST(HttpStatus.BAD_REQUEST, "C002", "이미 존재하는 카테고리 입니다."),
    NO_AUTHORITY(HttpStatus.UNAUTHORIZED, "C003", "권한이 없습니다."),;


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
