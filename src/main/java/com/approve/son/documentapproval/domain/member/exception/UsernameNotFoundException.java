package com.approve.son.documentapproval.domain.member.exception;


import com.approve.son.documentapproval.global.system.constants.ErrorCode;

public class UsernameNotFoundException extends RuntimeException {

    private ErrorCode errorCode;

    public UsernameNotFoundException(final ErrorCode message) {
        this.errorCode = message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}