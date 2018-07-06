package com.github.yexiaoxiaogo.usercenter_reconstruction.common.bean;

/**
 * 改绑管理员参数bean
 * 
 * @author hervoo
 *
 */
public class ReqChangeAdminBean {

    private String userId;// 新管理员用户id(新的管理员id)
    private String oldUserId;//原超级管理员id
    private String operatorId;// 操作人员id(即原来管理员用户id)
    private String companyId;// 公司id
    private String checkCode;// 验证码
    private String password;// 操作人员密码(原来的管理员密码)

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
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

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getOldUserId() {
		return oldUserId;
	}

	public void setOldUserId(String oldUserId) {
		this.oldUserId = oldUserId;
	}

}
