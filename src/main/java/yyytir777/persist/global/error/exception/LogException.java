package yyytir777.persist.global.error.exception;

import yyytir777.persist.global.error.ErrorCode;

public class LogException extends BusinessException{

    public LogException(ErrorCode errorCode) {
        super(errorCode);
    }
}
