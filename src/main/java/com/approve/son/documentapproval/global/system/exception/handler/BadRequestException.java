package com.approve.son.documentapproval.global.system.exception.handler;

import com.approve.son.documentapproval.global.system.constants.ErrorCode;

public class BadRequestException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public BadRequestException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
    public BadRequestException(final ErrorCode errorCode, final String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
    public String getMessage(){
        return message;
    }

}