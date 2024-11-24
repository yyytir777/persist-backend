package yyytir777.persist.global.error.exception;

import yyytir777.persist.global.error.ErrorCode;

public class CategoryException extends BusinessException {

    public CategoryException(ErrorCode errorCode) {
        super(errorCode);
    }
}
