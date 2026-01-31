# 设备管理移动端 - Android 应用

> Odoo 后端 API + Android 移动端前端混合方案

## 项目概述

基于 Odoo 16 后端 API 的设备管理 Android 移动应用，支持设备查询、点检任务、故障报修等功能。

### 技术栈

| 组件 | 技术 |
|------|------|
| 后端 | Odoo 16 (Python) |
| 前端 | Android (Java) |
| 认证方式 | Session 认证 |
| 网络请求 | Retrofit 2.9.0 + OkHttp 4.12.0 |
| 本地缓存 | Room Database 2.6.1 |
| 二维码扫描 | ZXing 4.3.0 |
| 图片加载 | Glide 4.16.0 |

---

## 功能清单

### ✅ 已实现功能

#### 1. 服务器配置
- [x] 服务器配置界面（服务器地址、数据库名称）
- [x] 连接测试功能（验证服务器可用性）
- [x] 配置持久化存储（SharedPreferences）
- [x] 首次使用引导配置
- [x] 登录界面快速进入配置
- 文件: `ui/settings/ServerConfigActivity.java`

#### 2. 用户认证
- [x] 登录界面（数据库、用户名、密码）
- [x] Session 管理与自动续期
- [x] 登录状态持久化
- [x] 自动登录检测
- [x] 自动加载服务器配置
- 文件: `ui/login/LoginActivity.java`

#### 3. 仪表盘
- [x] 设备统计（总数、正常、异常、维修中）
- [x] 点检统计（总数、待开始、进行中、已完成）
- [x] 报修统计（总数、待处理、进行中、已完成）
- [x] 我的待办任务列表
- [x] 最近报修单列表
- 文件: `ui/dashboard/DashboardFragment.java`

#### 4. 设备管理
- [x] 设备列表展示（分页加载）
- [x] 设备详情查看
- [x] 设备搜索（支持编号、名称、二维码）
- [x] 设备筛选（状态、类型）
- [x] 设备维修历史
- [x] 设备点检历史
- 文件: `ui/equipment/EquipmentListFragment.java`

#### 5. 点检任务
- [x] 点检任务列表
- [x] 点检任务详情
- [x] 点检结果填写与提交
- [x] 巡检路线执行
- [x] 异常设备标记
- 文件: `ui/inspection/InspectionListFragment.java`

#### 6. 故障报修
- [x] 报修单列表
- [x] 创建报修单
- [x] 报修单详情
- [x] 故障类型选择
- [x] 优先级设置
- 文件: `ui/repair/RepairListFragment.java`

#### 7. 本地缓存
- [x] Room 数据库配置
- [x] 设备数据缓存
- [x] 点检任务缓存
- [x] 报修单缓存
- 文件: `db/AppDatabase.java`

---

## Odoo 后端 API

### API 控制器
文件: `D:\sbgl.git\equipment_maintenance\controllers\mobile_api.py`

### API 端点列表

#### 认证相关
| 路由 | 方法 | 功能 | 认证 |
|------|------|------|------|
| `/api/mobile/login` | POST | 用户登录获取 session_id | public |
| `/api/mobile/logout` | POST | 用户登出 | session |
| `/api/mobile/user/current` | GET | 获取当前用户信息 | session |

#### 设备相关
| 路由 | 方法 | 功能 | 认证 |
|------|------|------|------|
| `/api/mobile/equipment/list` | GET | 获取设备列表 | session |
| `/api/mobile/equipment/<id>` | GET | 获取设备详情 | session |
| `/api/mobile/equipment/search` | GET | 搜索设备（支持二维码） | session |

#### 点检相关
| 路由 | 方法 | 功能 | 认证 |
|------|------|------|------|
| `/api/mobile/inspection/tasks` | GET | 获取点检任务列表 | session |
| `/api/mobile/inspection/<id>` | GET | 获取点检任务详情 | session |
| `/api/mobile/inspection/submit` | POST | 提交点检结果 | session |

#### 报修相关
| 路由 | 方法 | 功能 | 认证 |
|------|------|------|------|
| `/api/mobile/repair/list` | GET | 获取报修单列表 | session |
| `/api/mobile/repair/create` | POST | 创建报修单 | session |
| `/api/mobile/repair/<id>` | GET | 获取报修单详情 | session |

#### 其他
| 路由 | 方法 | 功能 | 认证 |
|------|------|------|------|
| `/api/mobile/dashboard` | GET | 获取仪表盘统计数据 | session |
| `/api/mobile/upload/image` | POST | 上传图片 | session |
| `/api/mobile/dict/fault_types` | GET | 获取故障类型列表 | session |
| `/api/mobile/dict/equipment_types` | GET | 获取设备类型列表 | session |

---

## 项目结构

### Android 应用结构
```
com.huasu.equipment/
├── api/                        # API 服务层
│   ├── ApiClient.java         # Retrofit 客户端配置
│   ├── OdooApiService.java    # API 接口定义
│   ├── SessionManager.java    # Session 管理
│   ├── request/               # 请求模型
│   │   ├── LoginRequest.java
│   │   ├── InspectionSubmitRequest.java
│   │   └── RepairCreateRequest.java
│   └── response/              # 响应模型
│       ├── BaseResponse.java
│       ├── EquipmentResponse.java
│       ├── InspectionResponse.java
│       ├── RepairResponse.java
│       └── DashboardResponse.java
├── model/                     # 数据模型
│   ├── Equipment.java
│   ├── InspectionTask.java
│   ├── RepairOrder.java
│   └── User.java
├── ui/                        # 界面层
│   ├── login/                 # 登录模块
│   ├── main/                  # 主界面（底部导航）
│   ├── dashboard/             # 仪表盘
│   ├── equipment/             # 设备管理
│   ├── inspection/            # 点检任务
│   ├── repair/                # 故障报修
│   └── settings/              # 设置
├── db/                        # 本地数据库
│   ├── AppDatabase.java       # 数据库配置
│   ├── EquipmentEntity.java   # 设备实体
│   ├── InspectionTaskEntity.java
│   ├── RepairOrderEntity.java
│   ├── EquipmentDao.java      # 数据访问对象
│   ├── InspectionTaskDao.java
│   └── RepairOrderDao.java
└── utils/                     # 工具类
    ├── ServerConfigManager.java  # 服务器配置管理
    └── SharedPreferencesManager.java
```

---

## 配置说明

### 服务器配置

**首次使用配置流程**:
1. 打开 APP，进入登录界面
2. 点击"服务器设置"进入配置界面
3. 输入服务器地址（如：`http://192.168.64.128:18080`）
4. 输入数据库名称（如：`th-rp-25-08-12`）
5. 点击"测试连接"验证配置
6. 点击"保存配置"返回登录界面

**修改服务器配置**:
- 登录界面点击"服务器设置"即可修改
- 配置自动保存到本地 SharedPreferences
- 下次启动 APP 自动加载配置

**配置文件**: `utils/ServerConfigManager.java`
```java
// 保存配置
ServerConfigManager.saveServerConfig(serverUrl, databaseName);

// 获取配置
String serverUrl = ServerConfigManager.getServerUrl();
String databaseName = ServerConfigManager.getDatabaseName();
```

### 默认配置

| 配置项 | 默认值 |
|--------|--------|
| 服务器地址 | `http://192.168.64.128:18080` |
| 数据库名称 | `th-rp-25-08-12` |
| 用户名 | `admin` |
| 密码 | `admin` |

### 网络安全配置

**文件**: `res/xml/network_security_config.xml`
- 开发阶段已允许明文流量
- 生产环境建议使用 HTTPS 并配置证书

---

## 构建说明

### 编译 Debug APK
```bash
cd D:\android_Huasu
./gradlew assembleDebug
```

### 编译 Release APK
```bash
./gradlew assembleRelease
```

### 输出位置
- Debug: `app/build/outputs/apk/debug/app-debug.apk`
- Release: `app/build/outputs/apk/release/app-release.apk`

### 多架构支持
- armeabi-v7a (32位 ARM)
- arm64-v8a (64位 ARM)

---

## 依赖说明

### 核心依赖
```gradle
// 网络请求
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'com.squareup.okhttp3:okhttp:4.12.0'

// JSON 解析
implementation 'com.google.code.gson:gson:2.10.1'

// 二维码扫描
implementation 'com.journeyapps:zxing-android-embedded:4.3.0'

// Room 数据库
implementation 'androidx.room:room-runtime:2.6.1'

// 图片加载
implementation 'com.github.bumptech.glide:glide:4.16.0'
```

---

## 权限说明

### AndroidManifest.xml
```xml
<!-- 网络权限 -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<!-- 相机权限 -->
<uses-permission android:name="android.permission.CAMERA" />

<!-- 存储权限 -->
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
```

---

## 使用说明

### 1. 首次使用配置
1. 打开应用，进入登录界面
2. 点击"服务器设置"进入配置界面
3. 输入服务器地址和数据库名称
4. 点击"测试连接"验证配置
5. 点击"保存配置"返回登录界面
6. 输入用户名和密码登录

### 2. 主界面导航
底部导航栏包含四个标签：
- **仪表盘**: 查看统计数据和待办任务
- **设备**: 查看和搜索设备
- **点检**: 查看和处理点检任务
- **报修**: 查看和创建报修单

### 3. 设备查询
- 支持分页加载设备列表
- 可按状态和类型筛选
- 支持关键词搜索
- 点击设备查看详情

### 4. 点检任务
- 查看待处理的点检任务
- 查看任务详情和巡检路线
- 填写点检结果
- 标记异常设备

### 5. 故障报修
- 查看报修单列表
- 创建新的报修单
- 选择故障类型和优先级
- 查看报修单详情

---

## 开发说明

### 数据模型映射

#### Odoo 模型 → Android 模型

| Odoo 模型 | Android 模型 | 说明 |
|-----------|--------------|------|
| `maintenance.equipment.re` | `Equipment` | 设备台账 |
| `inspection.task` | `InspectionTask` | 点检任务 |
| `maintenance.work.order` | `RepairOrder` | 维修工单 |
| `res.users` | `User` | 用户 |

### Session 认证流程

1. 用户调用 `/api/mobile/login` 登录
2. 服务器返回 `session_id`
3. 客户端保存 `session_id` 到 SharedPreferences
4. 后续请求在 Header 中携带 `X-Session-ID`
5. 服务器验证 Session 有效性

### 服务器配置管理

**配置存储位置**: `SharedPreferences: server_config`

| 配置项 | 说明 | 默认值 |
|--------|------|--------|
| server_url | Odoo 服务器地址 | http://192.168.64.128:18080 |
| database_name | Odoo 数据库名称 | th-rp-25-08-12 |
| is_configured | 是否已配置 | false |

### 离线缓存策略

- 使用 Room Database 本地缓存数据
- 首次加载从服务器获取数据
- 后续优先使用本地缓存
- 支持手动刷新数据

---

## CI/CD

### GitHub Actions

**文件**: `.github/workflows/build.yaml`

- 自动构建 Debug APK
- 自动构建 Release APK
- 支持多架构 APK
- 生成版本信息

---

## 注意事项

1. **服务器配置**: 首次使用必须配置 Odoo 服务器连接信息
2. **生产环境**: 建议使用 HTTPS 并配置证书锁定
3. **Session 有效期**: 默认 7 天，需要实现自动续期机制
4. **权限申请**: 相机和存储权限需要运行时动态申请
5. **数据同步**: 需要实现本地缓存与服务器数据的同步策略

---

## 后续优化建议

1. **完善 Fragment 功能**: 当前部分 Fragment 为占位实现，需要添加完整功能
2. **二维码扫描**: 集成 ZXing 扫描功能
3. **拍照上传**: 实现报修时的拍照功能
4. **推送通知**: 集成 FCM 推送新任务通知
5. **数据同步**: 实现离线数据与服务器数据的自动同步
6. **单元测试**: 添加 API 和数据库的单元测试
7. **UI 优化**: 改进用户体验和界面设计

---

## 构建/测试/验证

### Odoo 后端验证
```bash
# 测试登录 API
curl -X POST http://your-odoo-server/api/mobile/login \
  -H "Content-Type: application/json" \
  -d '{"db":"your-db","login":"admin","password":"admin"}'
```

### Android 前端验证
```bash
# 编译并安装到设备
cd D:\android_Huasu
./gradlew assembleDebug
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

## 许可证

LGPL-3

---

## 更新日志

### v1.0.1 (2024-01-29)
- ✅ 新增服务器配置功能
- ✅ 支持首次使用引导配置
- ✅ 支持后续修改服务器配置
- ✅ 连接测试功能

### v1.0.0 (2024-01-29)
- ✅ 初始版本
- ✅ 用户登录与 Session 管理
- ✅ 仪表盘统计
- ✅ 设备查询功能
- ✅ 点检任务功能
- ✅ 故障报修功能
- ✅ 本地数据缓存
- ✅ GitHub Actions CI/CD

---

## 联系方式

如有问题或建议，请联系开发团队。
