package com.github.yexiaoxiaogo.usercenter_reconstruction.common.constant;

/**
 * 账户类型枚举类
 * 
 * @author hervoo
 *
 */
public enum EnumUserType {

    platformUser("平台人员"), wmsUser("仓库人员");

    private String note;

    EnumUserType(String note) {
        this.note = note;
    }

}
