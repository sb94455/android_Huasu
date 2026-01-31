# 华数设备管理 - HBuilderX 使用指南

## 快速开始

### 1. 下载安装 HBuilderX

访问 [https://www.dcloud.io/hbuilderx.html](https://www.dcloud.io/hbuilderx.html) 下载并安装 HBuilderX

**注意**：选择"标准版"或"App开发版"

### 2. 导入项目

1. 打开 HBuilderX
2. 文件 → 导入 → 从本地目录导入
3. 选择 `D:\android_Huasu\huasu-equipment-uniapp` 目录

### 3. 运行到浏览器/模拟器/真机

#### 运行到浏览器（最快测试）
1. 点击工具栏"运行" → "运行到浏览器" → "Chrome"
2. 自动打开浏览器进行测试

#### 运行到微信小程序
1. 先安装[微信开发者工具](https://developers.weixin.qq.com/miniprogram/dev/devtools/download.html)
2. 点击"运行" → "运行到小程序模拟器" → "微信开发者工具"

#### 运行到 Android 模拟器
1. 点击"运行" → "运行到手机或模拟器" → "运行到 Android App 基座"
2. 需要先连接模拟器或真机

### 4. 云打包（需要 appid）

#### 获取 DCloud appid
1. 访问 [DCloud 开发者中心](https://dev.dcloud.net.cn/) 注册登录
2. 点击"创建应用"
3. 填写应用信息，获取 appid（格式如：`__UNIXXXXXX`）

#### 配置 appid
在 `src/manifest.json` 中填入你的 appid：
```json
{
  "appid": "__UNIXXXXXX"  // 你的 DCloud appid
}
```

#### 云打包步骤
1. 在 HBuilderX 中右键项目
2. 发行 → 原生App-云打包
3. 选择：
   - **Android**
   - 使用 DCloud 公共证书
   - 勾选"混淆压缩"
4. 点击"打包"，等待完成
5. 打包完成后下载 APK

### 5. 本地打包（无需 appid）

如果你不想申请 appid，可以使用本地打包：

#### 安装 Android SDK
1. 下载 [Android Studio](https://developer.android.com/studio)
2. 安装 Android SDK（建议 API Level 30+）
3. 配置环境变量 ANDROID_HOME

#### 本地打包
```bash
# 在项目目录执行
npm install
npm run build:app
```

生成的文件在 `unpackage/` 目录下。

## 常见问题

### Q: 云打包提示 appid 无效
A: 需要在 DCloud 开发者中心申请真实的 appid

### Q: 微信小程序需要什么
A: 需要在微信公众平台注册小程序，获取 appid

### Q: 如何快速测试
A: 使用"运行到浏览器"功能，无需配置，最快

### Q: 本地调试安卓
A: 使用"运行到 Android App 基座"，连接手机或模拟器
