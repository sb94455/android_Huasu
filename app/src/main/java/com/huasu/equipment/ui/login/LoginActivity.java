package com.huasu.equipment.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.huasu.equipment.R;
import com.huasu.equipment.api.ApiClient;
import com.huasu.equipment.api.SessionManager;
import com.huasu.equipment.api.request.LoginRequest;
import com.huasu.equipment.api.response.BaseResponse;
import com.huasu.equipment.ui.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etDatabase;
    private TextInputEditText etUsername;
    private TextInputEditText etPassword;
    private Button btnLogin;
    private TextView tvServerSettings;
    private TextView tvServerInfo;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 初始化 SessionManager
        SessionManager.init(this);

        // 检查是否已登录
        if (SessionManager.isLoggedIn()) {
            navigateToMain();
            return;
        }

        initViews();
        setupListeners();
    }

    private void initViews() {
        etDatabase = findViewById(R.id.etDatabase);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvServerSettings = findViewById(R.id.tvServerSettings);
        tvServerInfo = findViewById(R.id.tvServerInfo);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("登录中...");
        progressDialog.setCancelable(false);
    }

    private void setupListeners() {
        btnLogin.setOnClickListener(v -> attemptLogin());

        tvServerSettings.setOnClickListener(v -> showServerSettingsDialog());

        tvServerInfo.setText(getServerUrl());
    }

    private void attemptLogin() {
        // 重置错误
        etDatabase.setError(null);
        etUsername.setError(null);
        etPassword.setError(null);

        // 获取输入值
        String database = etDatabase.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // 验证输入
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("请输入密码");
            focusView = etPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(username)) {
            etUsername.setError("请输入用户名");
            focusView = etUsername;
            cancel = true;
        }

        if (TextUtils.isEmpty(database)) {
            etDatabase.setError("请输入数据库名称");
            focusView = etDatabase;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            performLogin(database, username, password);
        }
    }

    private void performLogin(String database, String username, String password) {
        progressDialog.show();

        LoginRequest request = new LoginRequest(database, username, password);
        Call<BaseResponse<LoginRequest.LoginData>> call = ApiClient.getApiService().login(request);

        call.enqueue(new Callback<BaseResponse<LoginRequest.LoginData>>() {
            @Override
            public void onResponse(Call<BaseResponse<LoginRequest.LoginData>> call,
                                 Response<BaseResponse<LoginRequest.LoginData>> response) {
                progressDialog.dismiss();

                if (response.isSuccessful() && response.body() != null) {
                    BaseResponse<LoginRequest.LoginData> baseResponse = response.body();

                    if (baseResponse.isSuccess()) {
                        LoginRequest.LoginData data = baseResponse.getData();

                        // 保存登录信息
                        SessionManager.saveLogin(
                                data.getSessionId(),
                                data.getUser().getId(),
                                data.getUser().getName(),
                                data.getUser().getEmail()
                        );

                        Toast.makeText(LoginActivity.this,
                                "登录成功，欢迎 " + data.getUser().getName(),
                                Toast.LENGTH_SHORT).show();

                        navigateToMain();
                    } else {
                        Toast.makeText(LoginActivity.this,
                                baseResponse.getError() != null ? baseResponse.getError() : "登录失败",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this,
                            "网络请求失败，请检查服务器连接",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<LoginRequest.LoginData>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,
                        "登录失败: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void showServerSettingsDialog() {
        // TODO: 实现服务器设置对话框
        Toast.makeText(this, "服务器设置功能待实现", Toast.LENGTH_SHORT).show();
    }

    private String getServerUrl() {
        // TODO: 从配置中读取服务器地址
        return "http://192.168.64.128:18080";
    }
}
