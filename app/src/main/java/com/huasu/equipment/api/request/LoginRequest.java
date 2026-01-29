package com.huasu.equipment.api.request;

import com.google.gson.annotations.SerializedName;

/**
 * 登录请求
 */
public class LoginRequest {

    @SerializedName("db")
    private String db;

    @SerializedName("login")
    private String login;

    @SerializedName("password")
    private String password;

    public LoginRequest(String db, String login, String password) {
        this.db = db;
        this.login = login;
        this.password = password;
    }

    // Getters
    public String getDb() { return db; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }

    /**
     * 登录响应数据
     */
    public static class LoginData {
        @SerializedName("session_id")
        private String sessionId;

        @SerializedName("user")
        private UserInfo user;

        public String getSessionId() { return sessionId; }
        public UserInfo getUser() { return user; }
    }

    public static class UserInfo {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        @SerializedName("email")
        private String email;

        @SerializedName("login")
        private String login;

        // Getters
        public int getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getLogin() { return login; }
    }
}
