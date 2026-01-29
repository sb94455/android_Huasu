package com.huasu.equipment.api;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Session 管理器
 * 管理 Session ID 和用户登录状态
 */
public class SessionManager {

    private static final String PREF_NAME = "equipment_session";
    private static final String KEY_SESSION_ID = "session_id";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    /**
     * 初始化
     */
    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    /**
     * 保存登录信息
     */
    public static void saveLogin(String sessionId, int userId, String userName, String userEmail) {
        editor.putString(KEY_SESSION_ID, sessionId);
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_USER_EMAIL, userEmail);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }

    /**
     * 获取 Session ID
     */
    public static String getSessionId() {
        if (sharedPreferences == null) {
            return null;
        }
        return sharedPreferences.getString(KEY_SESSION_ID, "");
    }

    /**
     * 获取用户 ID
     */
    public static int getUserId() {
        if (sharedPreferences == null) {
            return 0;
        }
        return sharedPreferences.getInt(KEY_USER_ID, 0);
    }

    /**
     * 获取用户名
     */
    public static String getUserName() {
        if (sharedPreferences == null) {
            return "";
        }
        return sharedPreferences.getString(KEY_USER_NAME, "");
    }

    /**
     * 获取用户邮箱
     */
    public static String getUserEmail() {
        if (sharedPreferences == null) {
            return "";
        }
        return sharedPreferences.getString(KEY_USER_EMAIL, "");
    }

    /**
     * 检查是否已登录
     */
    public static boolean isLoggedIn() {
        if (sharedPreferences == null) {
            return false;
        }
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    /**
     * 登出
     */
    public static void logout() {
        editor.clear();
        editor.apply();
    }
}
