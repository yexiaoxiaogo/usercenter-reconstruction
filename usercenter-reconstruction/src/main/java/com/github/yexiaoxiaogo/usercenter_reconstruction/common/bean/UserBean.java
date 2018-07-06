package com.github.yexiaoxiaogo.usercenter_reconstruction.common.bean;

/**
 * 修改用户信息UserBean
 * 
 * @author hervoo
 *
 */
public class UserBean extends SimpleUserBean {

    private String name;
    private String email;
    private String nickName;
    private String picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}
