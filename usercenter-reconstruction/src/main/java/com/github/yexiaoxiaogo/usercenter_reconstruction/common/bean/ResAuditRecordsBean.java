package com.github.yexiaoxiaogo.usercenter_reconstruction.common.bean;

import java.util.Date;

/**
 * 审核记录展示bean
 * 
 * @author hervoo
 *
 */
public class ResAuditRecordsBean {

    private String name;// 用户名
    private String phoneNum;// 手机号
    private Integer type;// 认证类型
    private Date applyTime;// 申请时间
    private Short auditResult;// 审核结果 -1-未通过 1-已通过0-待审核
    private String applicantId;// 认证id
    private String companyName;// 公司名称
    private Short status;// 冻结状态

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Short getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(Short auditResult) {
        this.auditResult = auditResult;
    }

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

}
