package com.approve.son.documentapproval.global.system.constants;

import com.fasterxml.jackson.annotation.JsonFormat;

import static com.approve.son.documentapproval.global.system.constants.CommonConstants.*;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, SUCCESS_CODE, " 잘못된 입력 값 입니다."),
    METHOD_NOT_ALLOWED(405, FAIL_CODE, " 지원하지 않는 메서드입니다."),
    INTERNAL_SERVER_ERROR(500, FAIL_CODE, "Server Error"),
    INVALID_TYPE_VALUE(400, FAIL_CODE, " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, FAIL_CODE, "Access is Denied"),
    BAD_REQUEST(400, SUCCESS_CODE, "입력 값을 확인해주세요. 올바르지 않은 형식의 데이터가 포함되어 있습니다."),
    NOT_FIND_USER_ID(403, SUCCESS_CODE, "아이디를 찾을 수 없습니다."),
    NOT_FIND_USER_PASSWORD(403, SUCCESS_CODE, "비밀번호를 확인해주세요."),

    // 커스텀 에러 0 정상 종료 , -1 추가 이벤트 X , -2 추가 이벤트 O (리다이렉트)
    INVALID_INPUT_EMAIL(200, SUCCESS_CODE, "잘못된 이메일 형식입니다."),
    NOT_FIND_DATA(200, ACTION_CODE, "잘못된 데이터 입니다. 새로고침 후 진행해주세요."),
    MAX_INPUT_DOCUMENT_TITLE(200, SUCCESS_CODE, "문서 제목은 50자 이하여야 됩니다."),
    INVALID_NOT_SAVE_DOCUMENT_STATUS(200, SUCCESS_CODE, "저장 상태만 가능한 이벤트 입니다."),
    DUPLICATE_APPROVAL_MEMBER_ID(200, SUCCESS_CODE, "중복된 결재 유저 ID가 있습니다."),
    DUPLICATE_APPROVAL_SORT(200, SUCCESS_CODE, "중복된 정렬 번호가 있습니다."),
    NOT_UPDATE_DATA_FIND_SORT(200, SUCCESS_CODE, "이전 결재자가 진행중입니다. 결재 순서를 기다려주세요."),
    NOT_UPDATE_DATA_ONLY_MY_ID(200, SUCCESS_CODE, "등록된 결재자 ID 만 수정 가능합니다."),
    NOT_UPDATE_DATA_ONLY_PROGRESS(200, SUCCESS_CODE, "진행중인 상태만 변경 가능합니다.")
    ;

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }


}
