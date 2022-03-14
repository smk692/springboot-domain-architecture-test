package com.approve.son.documentapproval.global.response;

import com.approve.son.documentapproval.global.system.constants.ErrorCode;
import lombok.Getter;

import static com.approve.son.documentapproval.global.system.constants.CommonConstants.SUCCESS_CODE;

@Getter
public class CommonResponse<T> {

    public String code;
    public String message;
    public T response;

    public CommonResponse(T response) {
        this.code = SUCCESS_CODE;
        this.message = "success";
        this.response = response;
    }

    public CommonResponse(final ErrorCode code) {
        StringBuilder resultMessage = new StringBuilder(code.getMessage());

        this.code = code.getCode();
        this.message = code.getMessage();
        this.response = null;
    }

    public CommonResponse(final ErrorCode code, final String message) {
        StringBuilder resultMessage = new StringBuilder(code.getMessage());

        if (!message.isBlank()) {
            resultMessage.append(" : ");
            resultMessage.append(this.message);
        }

        this.code = code.getCode();
        this.message = resultMessage.toString();
        this.response = null;
    }

    public static CommonResponse of(final ErrorCode code) {
        return new CommonResponse(code);
    }

    public static CommonResponse of(final ErrorCode code, final String message) {
        return new CommonResponse(code);
    }
}