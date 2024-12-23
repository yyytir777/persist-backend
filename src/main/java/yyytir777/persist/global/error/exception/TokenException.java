package yyytir777.persist.global.error.exception;

import yyytir777.persist.global.error.ErrorCode;

public class TokenException extends BusinessException {

    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}