package yyytir777.persist.global.error.exception;

import yyytir777.persist.global.error.ErrorCode;

public class MemberException extends BusinessException {

    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
