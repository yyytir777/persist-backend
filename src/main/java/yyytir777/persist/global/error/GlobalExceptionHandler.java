package yyytir777.persist.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import yyytir777.persist.global.error.exception.BusinessException;
import yyytir777.persist.global.response.ApiResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Business 예외 발생 시 호출
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<?> handlerBusinessException(BusinessException e) {
        log.info("[" + e.getClass().getSimpleName() + "] : " + e.getMessage() + " (ErrorCode : " + e.getErrorCode().getHttpStatus().value() + ")");
        return ApiResponse.onFailure(e.getErrorCode());
    }

    /**
     * 예상치 못한 예외 발생 시 호출
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handlerException(Exception e) {
        log.info("BusinessException : " + e.getMessage());
        return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
