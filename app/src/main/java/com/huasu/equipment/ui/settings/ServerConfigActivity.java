package com.huasu.equipment.ui.settings;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.huasu.equipment.R;
import com.huasu.equipment.api.ApiClient;
import com.huasu.equipment.api.request.LoginRequest;
import com.huasu.equipment.api.response.BaseResponse;
import com.huasu.equipment.utils.ServerConfigManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 服务器配置界面
 * 用于配置 Odoo 服务器连接信息
 */
public class ServerConfigActivity extends AppCompatActivity {

    private TextInputEditText etServerUrl;
    private TextInputEditText etDatabaseName;
    private Button btnTestConnection;
    private Button btnSaveConfig;
    private ImageView ivBack;
    private TextView tvConnectionStatus;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_config);

        // 初始化 ServerConfigManager
        ServerConfigManager.init(this);

        initViews();
        setupListeners();
        loadSavedConfig();
    }

    private void initViews() {
        ivBack = findViewById(R.id.ivBack);
        etServerUrl = findViewById(R.id.etServerUrl);
        etDatabaseName = findViewById(R.id.etDatabaseName);
        btnTestConnection = findViewById(R.id.btnTestConnection);
        btnSaveConfig = findViewById(R.id.btnSaveConfig);
        tvConnectionStatus = findViewById(R.id.tvConnectionStatus);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("连接中...");
        progressDialog.setCancelable(false);
    }

    private void setupListeners() {
        ivBack.setOnClickListener(v -> finish());

        btnTestConnection.setOnClickListener(v -> testConnection());

        btnSaveConfig.setOnClickListener(v -> saveConfig());
    }

    private void loadSavedConfig() {
        // 加载已保存的配置
        String serverUrl = ServerConfigManager.getServerUrl();
        String databaseName = ServerConfigManager.getDatabaseName();

        etServerUrl.setText(serverUrl);
        etDatabaseName.setText(databaseName);
    }

    private void testConnection() {
        String serverUrl = etServerUrl.getText().toString().trim();
        String databaseName = etDatabaseName.getText().toString().trim();

        // 验证输入
        if (TextUtils.isEmpty(serverUrl)) {
            Toast.makeText(this, "请输入服务器地址", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(databaseName)) {
            Toast.makeText(this, "请输入数据库名称", Toast.LENGTH_SHORT).show();
            return;
        }

        // 验证服务器地址格式
        if (!serverUrl.startsWith("http://") && !serverUrl.startsWith("https://")) {
            serverUrl = "http://" + serverUrl;
            etServerUrl.setText(serverUrl);
        }

        // 去掉末尾斜杠
        if (serverUrl.endsWith("/")) {
            serverUrl = serverUrl.substring(0, serverUrl.length() - 1);
            etServerUrl.setText(serverUrl);
        }

        tvConnectionStatus.setText("正在连接...");
        tvConnectionStatus.setTextColor(getColor(android.R.color.holo_blue_dark));

        // 使用测试账号验证连接
        progressDialog.show();

        // 临时设置服务器地址进行测试
        ApiClient.setBaseUrl(serverUrl);

        LoginRequest request = new LoginRequest(databaseName, "admin", "admin");
        Call<BaseResponse<LoginRequest.LoginData>> call = ApiClient.getApiService().login(request);

        call.enqueue(new Callback<BaseResponse<LoginRequest.LoginData>>() {
            @Override
            public void onResponse(Call<BaseResponse<LoginRequest.LoginData>> call,
                                 Response<BaseResponse<LoginRequest.LoginData>> response) {
                progressDialog.dismiss();

                if (response.isSuccessful() && response.body() != null) {
                    BaseResponse<LoginRequest.LoginData> baseResponse = response.body();

                    if (baseResponse.isSuccess()) {
                        tvConnectionStatus.setText("✓ 连接成功！服务器配置正确");
                        tvConnectionStatus.setTextColor(getColor(android.R.color.holo_green_dark));
                        Toast.makeText(ServerConfigActivity.this, "服务器连接成功", Toast.LENGTH_SHORT).show();
                    } else {
                        String error = baseResponse.getError() != null ? baseResponse.getError() : "连接失败";
                        tvConnectionStatus.setText("✗ 连接失败: " + error);
                        tvConnectionStatus.setTextColor(getColor(android.R.color.holo_red_dark));
                    }
                } else {
                    tvConnectionStatus.setText("✗ 无法连接到服务器，请检查地址是否正确");
                    tvConnectionStatus.setTextColor(getColor(android.R.color.holo_red_dark));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<LoginRequest.LoginData>> call, Throwable t) {
                progressDialog.dismiss();
                tvConnectionStatus.setText("✗ 连接失败: " + t.getMessage());
                tvConnectionStatus.setTextColor(getColor(android.R.color.holo_red_dark));
                Toast.makeText(ServerConfigActivity.this, "连接失败: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveConfig() {
        String serverUrl = etServerUrl.getText().toString().trim();
        String databaseName = etDatabaseName.getText().toString().trim();

        // 验证输入
        if (TextUtils.isEmpty(serverUrl)) {
            Toast.makeText(this, "请输入服务器地址", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(databaseName)) {
            Toast.makeText(this, "请输入数据库名称", Toast.LENGTH_SHORT).show();
            return;
        }

        // 验证服务器地址格式
        if (!serverUrl.startsWith("http://") && !serverUrl.startsWith("https://")) {
            serverUrl = "http://" + serverUrl;
        }

        // 去掉末尾斜杠
        if (serverUrl.endsWith("/")) {
            serverUrl = serverUrl.substring(0, serverUrl.length() - 1);
        }

        // 保存配置
        ServerConfigManager.saveServerConfig(serverUrl, databaseName);

        // 更新 ApiClient 的服务器地址
        ApiClient.setBaseUrl(serverUrl);

        Toast.makeText(this, "服务器配置已保存", Toast.LENGTH_SHORT).show();

        // 返回上一页
        finish();
    }
}
