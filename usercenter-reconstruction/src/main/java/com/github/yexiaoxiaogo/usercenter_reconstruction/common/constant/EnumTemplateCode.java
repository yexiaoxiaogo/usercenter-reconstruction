package com.github.yexiaoxiaogo.usercenter_reconstruction.common.constant;

/**
 * 短信模板枚举类
 * 
 * @author hervoo
 *
 */
public enum EnumTemplateCode {

    REGISTER("SMS_116785298"), CHANGEPWD("SMS_116785297"), PWD("SMS_122289553"), CHANGEINFO("SMS_116785296"), NOTICE("SMS_126461672"), CHANGEADMIN("SMS_126351533");

    private String code;

    EnumTemplateCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
