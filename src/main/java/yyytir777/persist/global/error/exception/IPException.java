package yyytir777.persist.global.error.exception;

import yyytir777.persist.global.error.ErrorCode;

public class IPException extends BusinessException{

    public IPException(ErrorCode errorCode) {
        super(errorCode);
    }
}
