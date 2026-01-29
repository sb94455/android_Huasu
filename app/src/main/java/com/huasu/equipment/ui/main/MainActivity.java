package com.huasu.equipment.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.huasu.equipment.R;
import com.huasu.equipment.api.SessionManager;
import com.huasu.equipment.ui.dashboard.DashboardFragment;
import com.huasu.equipment.ui.equipment.EquipmentListFragment;
import com.huasu.equipment.ui.inspection.InspectionListFragment;
import com.huasu.equipment.ui.login.LoginActivity;
import com.huasu.equipment.ui.repair.RepairListFragment;

/**
 * 主 Activity
 * 包含底部导航和 Fragment 切换
 */
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化 SessionManager
        SessionManager.init(this);

        // 检查登录状态
        if (!SessionManager.isLoggedIn()) {
            navigateToLogin();
            return;
        }

        setContentView(R.layout.activity_main);

        initViews();
        setupListeners();

        // 默认显示仪表盘
        if (savedInstanceState == null) {
            switchFragment(new DashboardFragment());
        }
    }

    private void initViews() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    private void setupListeners() {
        bottomNavigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_dashboard) {
                    switchFragment(new DashboardFragment());
                    return true;
                } else if (itemId == R.id.navigation_equipment) {
                    switchFragment(new EquipmentListFragment());
                    return true;
                } else if (itemId == R.id.navigation_inspection) {
                    switchFragment(new InspectionListFragment());
                    return true;
                } else if (itemId == R.id.navigation_repair) {
                    switchFragment(new RepairListFragment());
                    return true;
                }

                return false;
            }
        });
    }

    private void switchFragment(Fragment fragment) {
        if (fragment != currentFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();
            currentFragment = fragment;
        }
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
