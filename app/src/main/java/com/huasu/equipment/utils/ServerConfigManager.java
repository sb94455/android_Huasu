package com.huasu.equipment.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 服务器配置管理器
 * 管理 Odoo 服务器连接配置
 */
public class ServerConfigManager {

    private static final String PREF_NAME = "server_config";
    private static final String KEY_SERVER_URL = "server_url";
    private static final String KEY_DATABASE_NAME = "database_name";
    private static final String KEY_IS_CONFIGURED = "is_configured";

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
     * 保存服务器配置
     */
    public static void saveServerConfig(String serverUrl, String databaseName) {
        editor.putString(KEY_SERVER_URL, serverUrl);
        editor.putString(KEY_DATABASE_NAME, databaseName);
        editor.putBoolean(KEY_IS_CONFIGURED, true);
        editor.apply();
    }

    /**
     * 获取服务器 URL
     */
    public static String getServerUrl() {
        if (sharedPreferences == null) {
            return "http://192.168.64.128:18080";
        }
        return sharedPreferences.getString(KEY_SERVER_URL, "http://192.168.64.128:18080");
    }

    /**
     * 获取数据库名称
     */
    public static String getDatabaseName() {
        if (sharedPreferences == null) {
            return "th-rp-25-08-12";
        }
        return sharedPreferences.getString(KEY_DATABASE_NAME, "th-rp-25-08-12");
    }

    /**
     * 检查是否已配置
     */
    public static boolean isConfigured() {
        if (sharedPreferences == null) {
            return false;
        }
        return sharedPreferences.getBoolean(KEY_IS_CONFIGURED, false);
    }

    /**
     * 清除配置
     */
    public static void clearConfig() {
        editor.clear();
        editor.apply();
    }
}
