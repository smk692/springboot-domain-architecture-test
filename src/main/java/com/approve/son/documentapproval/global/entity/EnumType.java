package com.approve.son.documentapproval.global.entity;

public class EnumType {

    public enum DocumentType implements IEnumtype {
        VACATION_REPORT     ("휴가신청서"),
        EXPENSE_REPORT      ("지출결의서");
        private final String value;
        DocumentType(String value) { this.value = value; }

        @Override
        public String getKey() {
            return name();
        }
        @Override
        public String getValue(){
            return value;
        }
    }

    public enum DocumentStatus implements IEnumtype {
        SAVE                ("저장"),
        PROGRESS            ("결재 진행중"),
        REJECT              ("상신 반려"),
        COMPLETION          ("결재 완료");
        private final String value;
        DocumentStatus(String value) { this.value = value; }

        @Override
        public String getKey() {
            return name();
        }
        @Override
        public String getValue(){
            return value;
        }
    }

    public enum DocumentApprovalGroupStatus implements IEnumtype {
        UNCONFIRMED         ("미확인"),
        APPROVAL            ("승인"),
        REJECT              ("거절");
        private final String value;
        DocumentApprovalGroupStatus(String value) { this.value = value; }

        @Override
        public String getKey() {
            return name();
        }
        @Override
        public String getValue(){
            return value;
        }
    }

    public enum DocumentSearchType implements IEnumtype {
        OUTBOX              ("내가 생성한 문서 중 결재 진행 중인 문서"),
        INBOX               ("내가 결재를 해야 할 문서"),
        ARCHIVE             ("내가 관여한 문서 중 결재가 완료(승인 또는 거절)된 문서");
        private final String value;
        DocumentSearchType(String value) { this.value = value; }

        @Override
        public String getKey() {
            return name();
        }
        @Override
        public String getValue(){
            return value;
        }
    }


}
