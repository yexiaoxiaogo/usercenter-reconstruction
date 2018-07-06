package com.github.yexiaoxiaogo.usercenter_reconstruction.common.constant;

/**
 * 用户状态的枚举类
 * 
 * @author hervoo
 *
 */
public enum EnumUserStatus {

    NORMAL("正常"), DEL("注销"), FREEZING("冻结");

    private String note;

    EnumUserStatus(String note) {
        this.note = note;
    }

}
