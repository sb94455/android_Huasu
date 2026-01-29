package com.huasu.equipment.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 用户信息响应
 */
public class UserResponse implements Serializable {

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

    @SerializedName("company_id")
    private CompanyInfo companyId;

    public static class CompanyInfo implements Serializable {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        public int getId() { return id; }
        public String getName() { return name; }
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getLogin() { return login; }
    public String getPhone() { return phone; }
    public String getMobile() { return mobile; }
    public CompanyInfo getCompanyId() { return companyId; }
}
