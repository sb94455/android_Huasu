<template>
  <view class="server-config-page">
    <view class="container">
      <view class="card">
        <view class="card__title">服务器配置</view>
        <view class="card__content">
          <view class="input-group">
            <text class="input-group__label input-group__required">服务器地址</text>
            <input
              class="input"
              v-model="form.url"
              placeholder="例如: http://192.168.1.100:8069"
            />
            <text class="input-group__hint">请输入完整的服务器地址，包含端口号</text>
          </view>

          <view class="input-group">
            <text class="input-group__label input-group__required">数据库名</text>
            <input
              class="input"
              v-model="form.db"
              placeholder="请输入数据库名"
            />
            <text class="input-group__hint">Odoo数据库名称</text>
          </view>

          <view class="input-group">
            <text class="input-group__label">超时时间（秒）</text>
            <input
              class="input"
              v-model="form.timeout"
              type="number"
              placeholder="默认30秒"
            />
          </view>
        </view>
      </view>

      <view class="server-config-page__actions">
        <button class="btn btn--primary btn--block btn--large" @click="handleSave">
          保存配置
        </button>
        <button class="btn btn--default btn--block btn--large" @click="handleTest">
          测试连接
        </button>
      </view>

      <view class="server-config-page__info">
        <text class="server-config-page__info-title">配置说明</text>
        <text class="server-config-page__info-text">1. 服务器地址格式：http://IP:端口 或 https://域名</text>
        <text class="server-config-page__info-text">2. 默认端口为8069</text>
        <text class="server-config-page__info-text">3. 确保设备可以访问服务器网络</text>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useServerStore } from '@/stores/server'

interface ServerConfigForm {
  url: string
  db: string
  timeout: number
}

const serverStore = useServerStore()

const form = ref<ServerConfigForm>({
  url: 'http://192.168.1.100:8069',
  db: 'huasu_production',
  timeout: 30
})

// 加载保存的配置
onMounted(async () => {
  const config = await serverStore.loadConfig()
  if (config) {
    form.value.url = config.url
    form.value.db = config.db
  } else {
    const defaultConfig = serverStore.getDefaultConfig()
    form.value.url = defaultConfig.url
    form.value.db = defaultConfig.db
  }
})

// 保存配置
async function handleSave() {
  if (!form.value.url) {
    uni.showToast({ title: '请输入服务器地址', icon: 'none' })
    return
  }

  if (!form.value.db) {
    uni.showToast({ title: '请输入数据库名', icon: 'none' })
    return
  }

  // 简单的URL格式验证
  if (!form.value.url.startsWith('http://') && !form.value.url.startsWith('https://')) {
    uni.showToast({ title: '服务器地址格式错误', icon: 'none' })
    return
  }

  try {
    await serverStore.saveConfig({
      url: form.value.url,
      db: form.value.db
    })
    uni.showToast({ title: '保存成功', icon: 'success' })
    setTimeout(() => {
      uni.navigateBack()
    }, 500)
  } catch (error: any) {
    uni.showToast({
      title: error.message || '保存失败',
      icon: 'none'
    })
  }
}

// 测试连接
async function handleTest() {
  if (!form.value.url) {
    uni.showToast({ title: '请输入服务器地址', icon: 'none' })
    return
  }

  uni.showLoading({ title: '测试中...' })

  try {
    // 尝试访问服务器
    uni.request({
      url: `${form.value.url}/web/webclient/version_info`,
      method: 'GET',
      timeout: form.value.timeout * 1000,
      success: () => {
        uni.showToast({ title: '连接成功', icon: 'success' })
      },
      fail: () => {
        uni.showToast({ title: '连接失败，请检查配置', icon: 'none' })
      }
    })
  } finally {
    uni.hideLoading()
  }
}
</script>

<style lang="scss" scoped>
.server-config-page {
  min-height: 100vh;
  background: #f5f5f5;

  &__actions {
    display: flex;
    flex-direction: column;
    gap: 16rpx;
    margin: 32rpx 0;
  }

  &__info {
    background: #ffffff;
    border-radius: 16rpx;
    padding: 24rpx;
  }

  &__info-title {
    display: block;
    font-size: 28rpx;
    font-weight: 500;
    color: #333333;
    margin-bottom: 16rpx;
  }

  &__info-text {
    display: block;
    font-size: 26rpx;
    color: #666666;
    line-height: 1.8;
    margin-bottom: 8rpx;
  }
}

.input-group {
  &__hint {
    display: block;
    font-size: 24rpx;
    color: #999999;
    margin-top: 8rpx;
  }
}
</style>
