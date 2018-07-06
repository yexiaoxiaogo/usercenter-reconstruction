package com.github.yexiaoxiaogo.usercenter_reconstruction.common.constant;

/**
 * 短信类型的枚举类
 *
 * @author hervoo
 *
 */
public enum EnumMessageType {

    RV("注册验证码"), CP("密码修改验证码"), P("密码"), CA("修改信息"), CAD("改绑管理员");

    private String note;

    EnumMessageType(String note) {
        this.note = note;
    }

}
