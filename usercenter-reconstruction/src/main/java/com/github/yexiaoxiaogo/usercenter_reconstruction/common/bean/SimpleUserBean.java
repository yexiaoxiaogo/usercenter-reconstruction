package com.github.yexiaoxiaogo.usercenter_reconstruction.common.bean;

/**
 * 登录注册UserBean
 * 
 * @author hervoo
 *
 */
public class SimpleUserBean {

    private String id;// 用户id
    private String phoneNum;// 用户名(手机号)
    private String newPhoneNum;//新手机号
    private String checkCode;// 验证码
    private String password;// 密码
    private String newPassword;// 新密码
    private String remark;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getNewPhoneNum() {
		return newPhoneNum;
	}

	public void setNewPhoneNum(String newPhoneNum) {
		this.newPhoneNum = newPhoneNum;
	}

}
