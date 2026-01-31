# 华苏设备管理系统 - uni-app 版本

基于 uni-app + Vue 3 + TypeScript + Pinia 构建的跨平台移动应用，用于设备巡检、点检和报修管理。

## 技术栈

- **uni-app** - 跨平台应用开发框架
- **Vue 3.4+** - 渐进式 JavaScript 框架
- **TypeScript 5.0+** - JavaScript 的超集
- **Pinia** - Vue 状态管理库
- **uni-ui** - uni-app UI 组件库

## 功能特性

### 认证模块
- 用户登录/登出
- 自动登录
- 服务器配置管理

### 仪表盘
- 设备统计（总数、正常、故障、维修中）
- 点检统计（总数、待处理、已完成、今日任务）
- 报修统计（总数、待处理、处理中、已完成）
- 待办任务列表

### 设备管理
- 设备列表浏览
- 设备搜索（按名称/编号）
- 设备详情查看
- 设备维修历史
- 设备点检历史
- 扫码查看设备

### 点检管理
- 点检任务列表
- 按状态筛选（待处理/进行中/已完成）
- 任务详情查看
- 点检表单填写
- 结果提交

### 报修管理
- 报修单列表
- 按状态筛选
- 报修单详情
- 创建报修单
- 现场照片上传

## 项目结构

```
huasu-equipment-uniapp/
├── src/
│   ├── api/                    # API 接口层
│   │   ├── request.ts          # 请求封装
│   │   ├── types.ts            # 类型定义
│   │   ├── auth.ts             # 认证接口
│   │   ├── dashboard.ts        # 仪表盘接口
│   │   ├── equipment.ts        # 设备接口
│   │   ├── inspection.ts       # 点检接口
│   │   └── repair.ts           # 报修接口
│   ├── components/             # 公共组件
│   │   ├── StatCard.vue        # 统计卡片
│   │   ├── TaskCard.vue        # 任务卡片
│   │   └── EquipmentCard.vue   # 设备卡片
│   ├── pages/                  # 页面
│   │   ├── index/              # 登录页
│   │   ├── server-config/      # 服务器配置
│   │   ├── main/               # 主页
│   │   ├── dashboard/          # 仪表盘
│   │   ├── equipment/          # 设备管理
│   │   ├── inspection/         # 点检管理
│   │   └── repair/             # 报修管理
│   ├── stores/                 # Pinia 状态管理
│   │   ├── auth.ts             # 认证状态
│   │   ├── server.ts           # 服务器配置
│   │   ├── equipment.ts        # 设备状态
│   │   ├── inspection.ts       # 点检状态
│   │   └── repair.ts           # 报修状态
│   ├── types/                  # TypeScript 类型
│   │   ├── api.d.ts            # API 类型
│   │   └── model.d.ts          # 数据模型类型
│   ├── utils/                  # 工具函数
│   │   ├── storage.ts          # 本地存储
│   │   ├── validator.ts        # 表单验证
│   │   └── constants.ts        # 常量定义
│   ├── styles/                 # 全局样式
│   │   └── index.scss          # 样式入口
│   ├── App.vue                 # 根组件
│   ├── main.ts                 # 应用入口
│   ├── pages.json              # 页面配置
│   └── manifest.json           # 应用配置
├── package.json
├── tsconfig.json
└── vite.config.ts
```

## 快速开始

### 环境要求

- Node.js >= 16.x
- npm >= 8.x 或 pnpm >= 7.x

### 安装依赖

```bash
npm install
```

### 开发运行

#### H5 端
```bash
npm run dev:h5
```

#### 微信小程序
```bash
npm run dev:mp-weixin
```

#### App
```bash
npm run dev:app
```

### 打包发布

#### H5 端
```bash
npm run build:h5
```

#### 微信小程序
```bash
npm run build:mp-weixin
```

#### App
```bash
npm run build:app
```

## 配置说明

### 服务器配置

在登录页面或服务器配置页面配置后端地址：

- **服务器地址**: Odoo 服务器地址，如 `http://192.168.1.100:8069`
- **数据库名**: Odoo 数据库名称，如 `huasu_production`

### API 兼容性

本应用复用现有 Android 应用的 Odoo 后端 API，无需修改后端代码。

### Android 打包配置

#### DCloud 应用信息

| 项目 | 值 |
|------|-----|
| **应用名称** | 华苏设备管理 |
| **DCloud appid** | `__UNI__CF33ED8` |
| **版本号** | 1.0.0 |
| **版本代码** | 100 |

#### Android 证书信息

| 项目 | 值 |
|------|-----|
| **包名** | `com.huasu.equipment` |
| **SHA1** | `F4:10:20:7A:EA:78:75:B7:8F:7A:37:FF:0D:CF:8A:E6:37:0E:58:9F` |
| **SHA256** | `CA:30:54:8E:C4:A4:1B:90:E3:C1:B8:D6:A4:39:C6:53:71:38:4E:6D:B9:2C:A9:09:70:22:1E:0D:0A:DB:34:15` |
| **证书文件** | `huasu.keystore` (位于项目根目录) |
| **证书密码** | `123456` |
| **别名** | `huasu` |

#### HBuilderX 云打包步骤

1. **配置 DCloud 后台**
   - 访问 https://dev.dcloud.net.cn/
   - 进入应用 → 各平台详情 → Android
   - 填写包名和 SHA1 值

2. **云打包**
   - 右键项目 → 发行 → 原生App-云打包
   - Android → 使用自有证书
   - 证书文件：`huasu.keystore`
   - 证书密码：`huasu123`

## 开发指南

### 添加新页面

1. 在 `src/pages/` 下创建页面目录和 Vue 文件
2. 在 `src/pages.json` 中注册页面路由

```json
{
  "pages": [
    {
      "path": "pages/your-page/index",
      "style": {
        "navigationBarTitleText": "页面标题"
      }
    }
  ]
}
```

### 添加新 API

在 `src/api/` 下创建对应的 API 文件：

```typescript
import { request } from './request'

export const yourApi = {
  async getData() {
    return request.get('/your-endpoint')
  }
}
```

### 添加新的状态管理

在 `src/stores/` 下创建对应的 store 文件：

```typescript
import { defineStore } from 'pinia'

export const useYourStore = defineStore('your', {
  state: () => ({
    // 状态定义
  }),

  actions: {
    // 操作方法
  }
})
```

## 常见问题

### 1. 网络请求失败

- 确认服务器地址配置正确
- 确认设备可以访问服务器网络
- 检查 Odoo 服务是否正常运行

### 2. 登录失败

- 确认用户名和密码正确
- 确认数据库名称正确
- 检查用户是否有移动端访问权限

### 3. 图片上传失败

- 确认已授予相机/相册权限
- 检查图片大小是否超过限制（10MB）
- 确认服务器配置正确

## 许可证

Copyright © 2024 华苏科技
