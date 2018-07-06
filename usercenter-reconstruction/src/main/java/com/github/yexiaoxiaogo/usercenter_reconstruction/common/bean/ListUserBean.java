package com.github.yexiaoxiaogo.usercenter_reconstruction.common.bean;

/**
 * 用户查询条件bean
 * 
 * @author hervoo
 *
 */
public class ListUserBean {

    private String status;
    private String name;
    private String nickName;
    private Integer pageNo;
    private Integer pageSize;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

}
