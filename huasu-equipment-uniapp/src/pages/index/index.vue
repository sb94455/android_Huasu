<template>
  <view class="login-page">
    <view class="login-page__header">
      <text class="login-page__title">设备管理</text>
      <text class="login-page__subtitle">请登录您的账户</text>
    </view>

    <view class="login-page__form">
      <view class="input-group">
        <text class="input-group__label input-group__required">服务器地址</text>
        <input
          class="input"
          v-model="form.serverUrl"
          placeholder="请输入服务器地址"
          :disabled="loading"
        />
      </view>

      <view class="input-group">
        <text class="input-group__label input-group__required">数据库名</text>
        <input
          class="input"
          v-model="form.db"
          placeholder="请输入数据库名"
          :disabled="loading"
        />
      </view>

      <view class="input-group">
        <text class="input-group__label input-group__required">用户名</text>
        <input
          class="input"
          v-model="form.username"
          placeholder="请输入用户名"
          :disabled="loading"
        />
      </view>

      <view class="input-group">
        <text class="input-group__label input-group__required">密码</text>
        <input
          class="input"
          v-model="form.password"
          type="password"
          placeholder="请输入密码"
          :disabled="loading"
        />
      </view>

      <view class="login-page__actions">
        <button class="btn btn--primary btn--block btn--large" :disabled="loading" @click="handleLogin">
          {{ loading ? '登录中...' : '登录' }}
        </button>
      </view>

      <view class="login-page__links">
        <text class="login-page__link" @click="goToServerConfig">服务器配置</text>
      </view>
    </view>

    <view class="login-page__footer">
      <text class="login-page__version">版本 1.0.0</text>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { useServerStore } from '@/stores/server'

interface LoginForm {
  serverUrl: string
  db: string
  username: string
  password: string
}

const authStore = useAuthStore()
const serverStore = useServerStore()

const form = ref<LoginForm>({
  serverUrl: '',
  db: '',
  username: '',
  password: ''
})

const loading = ref(false)

// 加载保存的配置
async function loadSavedConfig() {
  const config = await serverStore.loadConfig()
  if (config) {
    form.value.serverUrl = config.url
    form.value.db = config.db
  } else {
    // 使用默认配置
    const defaultConfig = serverStore.getDefaultConfig()
    form.value.serverUrl = defaultConfig.url
    form.value.db = defaultConfig.db
  }
}

// 登录处理
async function handleLogin() {
  // 验证表单
  if (!form.value.serverUrl) {
    uni.showToast({ title: '请输入服务器地址', icon: 'none' })
    return
  }
  if (!form.value.db) {
    uni.showToast({ title: '请输入数据库名', icon: 'none' })
    return
  }
  if (!form.value.username) {
    uni.showToast({ title: '请输入用户名', icon: 'none' })
    return
  }
  if (!form.value.password) {
    uni.showToast({ title: '请输入密码', icon: 'none' })
    return
  }

  loading.value = true

  try {
    // 保存服务器配置
    await serverStore.saveConfig({
      url: form.value.serverUrl,
      db: form.value.db
    })

    // 执行登录
    await authStore.login(form.value.db, form.value.username, form.value.password)

    uni.showToast({ title: '登录成功', icon: 'success' })

    // 跳转到主页
    setTimeout(() => {
      uni.reLaunch({ url: '/pages/main/index' })
    }, 500)
  } catch (error: any) {
    uni.showToast({
      title: error.message || '登录失败',
      icon: 'none',
      duration: 2000
    })
  } finally {
    loading.value = false
  }
}

// 跳转到服务器配置页
function goToServerConfig() {
  uni.navigateTo({ url: '/pages/server-config/index' })
}

// 页面加载时初始化
onMounted(() => {
  loadSavedConfig()
})
</script>

<style lang="scss" scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  padding: 80rpx 48rpx;
  background: linear-gradient(135deg, #1890ff 0%, #096dd9 100%);

  &__header {
    text-align: center;
    margin-bottom: 80rpx;
  }

  &__title {
    display: block;
    font-size: 48rpx;
    font-weight: bold;
    color: #ffffff;
    margin-bottom: 16rpx;
  }

  &__subtitle {
    display: block;
    font-size: 28rpx;
    color: rgba(255, 255, 255, 0.8);
  }

  &__form {
    background: #ffffff;
    border-radius: 24rpx;
    padding: 48rpx;
    box-shadow: 0 8rpx 24rpx rgba(0, 0, 0, 0.1);
  }

  &__actions {
    margin-top: 48rpx;
  }

  &__links {
    display: flex;
    justify-content: center;
    margin-top: 32rpx;
  }

  &__link {
    font-size: 28rpx;
    color: #1890ff;
  }

  &__footer {
    margin-top: auto;
    text-align: center;
    padding-top: 48rpx;
  }

  &__version {
    font-size: 24rpx;
    color: rgba(255, 255, 255, 0.6);
  }
}
</style>
