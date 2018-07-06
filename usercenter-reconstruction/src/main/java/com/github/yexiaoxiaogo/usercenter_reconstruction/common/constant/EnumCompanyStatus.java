package com.github.yexiaoxiaogo.usercenter_reconstruction.common.constant;

public enum EnumCompanyStatus {

    UNDERREVIEW((short) 0, "待审批"), CERTIFIED((short) 1, "已认证"), FAILED((short) -1, "已退回"), FREEZE((short) 2, "已冻结");

    private Short code;
    private String note;

    public Short getCode() {
        return code;
    }

    public void setCode(Short code) {
        this.code = code;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private EnumCompanyStatus(Short code, String note) {
        this.code = code;
        this.note = note;
    }
}
