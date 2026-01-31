<template>
  <view class="main-page">
    <view class="main-page__header">
      <text class="main-page__title">华数设备管理</text>
      <text class="main-page__user">{{ authStore.userName }}</text>
    </view>

    <view class="main-page__content">
      <view class="main-page__grid">
        <view class="main-page__grid-item" @click="navigateTo('/pages/dashboard/index')">
          <view class="main-page__grid-icon" style="background: rgba(24, 144, 255, 0.1);">
            <text style="font-size: 48rpx;">📊</text>
          </view>
          <text class="main-page__grid-label">仪表盘</text>
        </view>

        <view class="main-page__grid-item" @click="navigateTo('/pages/equipment/list')">
          <view class="main-page__grid-icon" style="background: rgba(82, 196, 26, 0.1);">
            <text style="font-size: 48rpx;">🔧</text>
          </view>
          <text class="main-page__grid-label">设备管理</text>
        </view>

        <view class="main-page__grid-item" @click="navigateTo('/pages/inspection/list')">
          <view class="main-page__grid-icon" style="background: rgba(250, 173, 20, 0.1);">
            <text style="font-size: 48rpx;">✅</text>
          </view>
          <text class="main-page__grid-label">点检管理</text>
        </view>

        <view class="main-page__grid-item" @click="navigateTo('/pages/repair/list')">
          <view class="main-page__grid-icon" style="background: rgba(255, 77, 79, 0.1);">
            <text style="font-size: 48rpx;">🔴</text>
          </view>
          <text class="main-page__grid-label">报修管理</text>
        </view>
      </view>
    </view>

    <view class="main-page__footer">
      <button class="btn btn--default btn--block" @click="handleLogout">
        退出登录
      </button>
    </view>
  </view>
</template>

<script setup lang="ts">
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()

// 页面跳转
function navigateTo(url: string) {
  // tabBar 页面使用 switchTab
  const tabBarPages = ['/pages/dashboard/index', '/pages/equipment/list', '/pages/inspection/list', '/pages/repair/list']
  if (tabBarPages.includes(url)) {
    uni.switchTab({ url })
  } else {
    uni.navigateTo({ url })
  }
}

// 退出登录
async function handleLogout() {
  uni.showModal({
    title: '确认退出',
    content: '确定要退出登录吗？',
    success: async (res) => {
      if (res.confirm) {
        await authStore.logout()
        uni.reLaunch({ url: '/pages/index/index' })
      }
    }
  })
}
</script>

<style lang="scss" scoped>
.main-page {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: #f5f5f5;

  &__header {
    background: linear-gradient(135deg, #1890ff 0%, #096dd9 100%);
    padding: 48rpx 24rpx;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  &__title {
    font-size: 36rpx;
    font-weight: bold;
    color: #ffffff;
  }

  &__user {
    font-size: 28rpx;
    color: rgba(255, 255, 255, 0.8);
  }

  &__content {
    flex: 1;
    padding: 24rpx;
  }

  &__grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 24rpx;
  }

  &__grid-item {
    background: #ffffff;
    border-radius: 16rpx;
    padding: 48rpx 24rpx;
    display: flex;
    flex-direction: column;
    align-items: center;
    box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
    transition: all 0.3s;

    &:active {
      transform: scale(0.98);
      opacity: 0.8;
    }
  }

  &__grid-icon {
    width: 112rpx;
    height: 112rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 50%;
    margin-bottom: 16rpx;
  }

  &__grid-label {
    font-size: 28rpx;
    color: #333333;
    font-weight: 500;
  }

  &__footer {
    padding: 24rpx;
    padding-bottom: calc(24rpx + env(safe-area-inset-bottom));
  }
}
</style>
