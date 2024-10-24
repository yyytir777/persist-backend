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

    // Log
    LOG_NOT_EXIST(HttpStatus.BAD_REQUEST, "L001", "존재하지 않는 로그입니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
