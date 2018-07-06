package com.github.yexiaoxiaogo.usercenter_reconstruction.common.bean;

import java.util.List;

public class RoleRightBean {
	
	private Integer roleId;
	private String roleName;
	private String depict;
	private String remark;
	private String module;
    private String owner;
	private List<Integer> rightIds;
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDepict() {
		return depict;
	}
	public void setDepict(String depict) {
		this.depict = depict;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<Integer> getRightIds() {
		return rightIds;
	}
	public void setRightIds(List<Integer> rightIds) {
		this.rightIds = rightIds;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}

}
