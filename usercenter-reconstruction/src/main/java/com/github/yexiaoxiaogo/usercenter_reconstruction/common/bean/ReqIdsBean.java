package com.github.yexiaoxiaogo.usercenter_reconstruction.common.bean;

import java.util.List;

/**
 * id集合bean
 * 
 * @author hervoo
 *
 */
public class ReqIdsBean {

    private String id;// 用户id
    private List<Integer> roleIds;// 角色ids
    private List<Integer> depIds;// 部门ids

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public List<Integer> getDepIds() {
        return depIds;
    }

    public void setDepIds(List<Integer> depIds) {
        this.depIds = depIds;
    }

}
