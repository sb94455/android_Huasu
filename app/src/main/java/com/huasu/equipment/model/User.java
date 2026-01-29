package com.huasu.equipment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 用户数据模型
 */
public class User implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("login")
    private String login;

    @SerializedName("phone")
    private String phone;

    @SerializedName("mobile")
    private String mobile;

    // 本地存储字段
    private String sessionId;
    private boolean isLoggedIn;
    private long lastLoginTime;

    public User() {
        this.lastLoginTime = System.currentTimeMillis();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public boolean isLoggedIn() { return isLoggedIn; }
    public void setLoggedIn(boolean loggedIn) { isLoggedIn = loggedIn; }

    public long getLastLoginTime() { return lastLoginTime; }
    public void setLastLoginTime(long lastLoginTime) { this.lastLoginTime = lastLoginTime; }

    /**
     * 获取显示名称
     */
    public String getDisplayName() {
        if (name != null && !name.isEmpty()) {
            return name;
        }
        return login;
    }

    /**
     * 获取联系电话
     */
    public String getContactPhone() {
        if (mobile != null && !mobile.isEmpty()) {
            return mobile;
        }
        return phone;
    }
}
