package yyytir777.persist.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import yyytir777.persist.global.error.exception.BusinessException;
import yyytir777.persist.global.error.exception.MemberException;
import yyytir777.persist.global.error.exception.IPException;
import yyytir777.persist.global.error.exception.TokenException;
import yyytir777.persist.global.response.ApiResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("[" + e.getClass().getSimpleName() + "] : " + e.getMessage());
        return new ApiResponse<>(false, HttpStatus.BAD_REQUEST.toString(), null, e.getMessage());
    }

    @ExceptionHandler(MemberException.class)
    public ApiResponse<?> handlerMemberException(MemberException e) {
        log.info("[" + e.getClass().getSimpleName() + "] : " + e.getMessage() + " (ErrorCode : " + e.getErrorCode().getHttpStatus().value() + ")");
        return new ApiResponse<>(false, e.getErrorCode().getCode(), null, e.getErrorCode().getMessage());
    }

    @ExceptionHandler(IPException.class)
    public ApiResponse<?> handlerIPException(IPException e) {
        log.warn("[" + e.getClass().getSimpleName() + "] : " + e.getMessage() + " (ErrorCode : " + e.getErrorCode().getHttpStatus().value() + ")");
        return ApiResponse.onFailure(e.getErrorCode());
    }

    @ExceptionHandler(TokenException.class)
    public ApiResponse<?> handlerTokenException(TokenException e) {
        log.info("[" + e.getClass().getSimpleName() + "] : " + e.getMessage() + " (ErrorCode : " + e.getErrorCode().getHttpStatus().value() + ")");
        return new ApiResponse<>(false, e.getErrorCode().getCode(), null, e.getErrorCode().getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ApiResponse<?> handlerBusinessException(BusinessException e) {
        log.warn("[" + e.getClass().getSimpleName() + "] : " + e.getMessage() + " (ErrorCode : " + e.getErrorCode().getHttpStatus().value() + ")");
        return ApiResponse.onFailure(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handlerException(Exception e) {
        log.error("Exception : " + e.getMessage());
        return ApiResponse.onFailure(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
