package yyytir777.persist.global.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    TEST(HttpStatus.INTERNAL_SERVER_ERROR, "E001", "Business Error");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
