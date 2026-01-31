package com.huasu.equipment.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * API 客户端
 * 单例模式管理 Retrofit 实例
 */
public class ApiClient {

    private static String BASE_URL = "http://192.168.64.128:18080/";

    private static Retrofit retrofit = null;
    private static OdooApiService apiService = null;
    private static OkHttpClient okHttpClient = null;

    /**
     * 获取 Retrofit 实例
     */
    private static Retrofit getClient() {
        if (retrofit == null) {
            // 日志拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // OkHttp 客户端配置
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(chain -> {
                        // 添加 Session ID 到请求头
                        String sessionId = SessionManager.getSessionId();
                        if (sessionId != null && !sessionId.isEmpty()) {
                            return chain.proceed(chain.request().newBuilder()
                                    .addHeader("X-Session-ID", sessionId)
                                    .build());
                        }
                        return chain.proceed(chain.request());
                    })
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();

            // Gson 配置
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();

            // Retrofit 实例
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    /**
     * 获取 API 服务实例
     */
    public static OdooApiService getApiService() {
        if (apiService == null) {
            apiService = getClient().create(OdooApiService.class);
        }
        return apiService;
    }

    /**
     * 设置自定义 Base URL (用于测试环境切换)
     */
    public static void setBaseUrl(String baseUrl) {
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        BASE_URL = baseUrl;
        // 重新创建 Retrofit 实例
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = null;
    }

    /**
     * 获取当前 Base URL
     */
    public static String getBaseUrl() {
        return BASE_URL;
    }
}
