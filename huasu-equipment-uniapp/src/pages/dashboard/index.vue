<template>
  <view class="dashboard-page">
    <!-- 顶部导航栏 -->
    <view class="dashboard-page__header">
      <text class="dashboard-page__title">仪表盘</text>
      <text class="dashboard-page__logout" @click="handleLogout">退出</text>
    </view>

    <view class="container">
      <!-- 设备统计 -->
      <view class="dashboard-section">
        <view class="dashboard-section__title">设备统计</view>
        <view class="dashboard-section__content">
          <stat-card
            icon="🔧"
            icon-bg="rgba(24, 144, 255, 0.1)"
            :value="stats.equipment?.total || 0"
            label="设备总数"
            clickable
            @click="navigateToEquipment"
          />
          <stat-card
            icon="✅"
            icon-bg="rgba(82, 196, 26, 0.1)"
            :value="stats.equipment?.active || 0"
            label="正常设备"
          />
          <stat-card
            icon="⚠️"
            icon-bg="rgba(255, 77, 79, 0.1)"
            :value="stats.equipment?.broken || 0"
            label="故障设备"
            clickable
            @click="navigateToBrokenEquipment"
          />
          <stat-card
            icon="🔨"
            icon-bg="rgba(250, 173, 20, 0.1)"
            :value="stats.equipment?.maintenance || 0"
            label="维修中"
          />
        </view>
      </view>

      <!-- 点检统计 -->
      <view class="dashboard-section">
        <view class="dashboard-section__title">点检统计</view>
        <view class="dashboard-section__content">
          <stat-card
            icon="📋"
            icon-bg="rgba(24, 144, 255, 0.1)"
            :value="stats.inspection?.total || 0"
            label="点检任务"
            clickable
            @click="navigateToInspection"
          />
          <stat-card
            icon="⏳"
            icon-bg="rgba(250, 173, 20, 0.1)"
            :value="stats.inspection?.pending || 0"
            label="待处理"
          />
          <stat-card
            icon="✅"
            icon-bg="rgba(82, 196, 26, 0.1)"
            :value="stats.inspection?.completed || 0"
            label="已完成"
          />
          <stat-card
            icon="📅"
            icon-bg="rgba(114, 46, 209, 0.1)"
            :value="stats.inspection?.today || 0"
            label="今日任务"
          />
        </view>
      </view>

      <!-- 报修统计 -->
      <view class="dashboard-section">
        <view class="dashboard-section__title">报修统计</view>
        <view class="dashboard-section__content">
          <stat-card
            icon="🔴"
            icon-bg="rgba(24, 144, 255, 0.1)"
            :value="stats.repair?.total || 0"
            label="报修单"
            clickable
            @click="navigateToRepair"
          />
          <stat-card
            icon="⏳"
            icon-bg="rgba(250, 173, 20, 0.1)"
            :value="stats.repair?.pending || 0"
            label="待处理"
          />
          <stat-card
            icon="🔧"
            icon-bg="rgba(114, 46, 209, 0.1)"
            :value="stats.repair?.in_progress || 0"
            label="处理中"
          />
          <stat-card
            icon="✅"
            icon-bg="rgba(82, 196, 26, 0.1)"
            :value="stats.repair?.completed || 0"
            label="已完成"
          />
        </view>
      </view>

      <!-- 待办任务 -->
      <view v-if="pendingTasks.inspections.length > 0 || pendingTasks.repairs.length > 0" class="dashboard-section">
        <view class="dashboard-section__title">待办任务</view>
        <view class="dashboard-section__content">
          <view v-if="pendingTasks.inspections.length > 0" class="dashboard-subsection">
            <view class="dashboard-subsection__title">点检任务</view>
            <task-card
              v-for="task in pendingTasks.inspections.slice(0, 3)"
              :key="task.id"
              :task="task"
              type="inspection"
              @click="navigateToInspectionDetail(task.id)"
            />
          </view>

          <view v-if="pendingTasks.repairs.length > 0" class="dashboard-subsection">
            <view class="dashboard-subsection__title">报修单</view>
            <task-card
              v-for="order in pendingTasks.repairs.slice(0, 3)"
              :key="order.id"
              :task="order"
              type="repair"
              @click="navigateToRepairDetail(order.id)"
            />
          </view>
        </view>
      </view>
    </view>

    <!-- 下拉刷新 -->
    <view class="dashboard-page__refresh" @click="loadData">
      <text class="dashboard-page__refresh-text">{{ loading ? '加载中...' : '下拉刷新' }}</text>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { dashboardApi } from '@/api/dashboard'
import type { DashboardStats } from '@/types'

interface Stats {
  equipment: {
    total: number
    active: number
    broken: number
    maintenance: number
  }
  inspection: {
    total: number
    pending: number
    completed: number
    today: number
  }
  repair: {
    total: number
    pending: number
    in_progress: number
    completed: number
  }
}

const stats = ref<Stats>({
  equipment: { total: 0, active: 0, broken: 0, maintenance: 0 },
  inspection: { total: 0, pending: 0, completed: 0, today: 0 },
  repair: { total: 0, pending: 0, in_progress: 0, completed: 0 }
})

const pendingTasks = ref<{
  inspections: any[]
  repairs: any[]
}>({ inspections: [], repairs: [] })

const loading = ref(false)

// 加载数据
async function loadData() {
  if (loading.value) return

  loading.value = true

  try {
    const data = await dashboardApi.getStats()
    stats.value = data
    pendingTasks.value = data.pendingTasks || { inspections: [], repairs: [] }
  } catch (error: any) {
    uni.showToast({
      title: error.message || '加载数据失败',
      icon: 'none'
    })
  } finally {
    loading.value = false
  }
}

// 导航方法
function navigateToEquipment() {
  uni.switchTab({ url: '/pages/equipment/list' })
}

function navigateToBrokenEquipment() {
  uni.switchTab({
    url: '/pages/equipment/list?status=broken'
  })
}

function navigateToInspection() {
  uni.switchTab({ url: '/pages/inspection/list' })
}

function navigateToInspectionDetail(id: number) {
  uni.navigateTo({ url: `/pages/inspection/detail?id=${id}` })
}

function navigateToRepair() {
  uni.switchTab({ url: '/pages/repair/list' })
}

function navigateToRepairDetail(id: number) {
  uni.navigateTo({ url: `/pages/repair/detail?id=${id}` })
}

// 退出登录
function handleLogout() {
  uni.showModal({
    title: '确认退出',
    content: '确定要退出登录吗？',
    success: async (res) => {
      if (res.confirm) {
        // 调用 authStore 的 logout
        const { useAuthStore } = await import('@/stores/auth')
        const authStore = useAuthStore()
        await authStore.logout()
        uni.reLaunch({ url: '/pages/index/index' })
      }
    }
  })
}

onMounted(() => {
  loadData()
})

// 下拉刷新
// #ifndef H5
onPullDownRefresh(() => {
  loadData().finally(() => {
    uni.stopPullDownRefresh()
  })
})
// #endif
</script>

<style lang="scss" scoped>
.dashboard-page {
  min-height: 100vh;
  background: #f5f5f5;
  padding-bottom: 100rpx;

  &__header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 24rpx;
    background: #ffffff;
    border-bottom: 1rpx solid #e8e8e8;
  }

  &__title {
    font-size: 36rpx;
    font-weight: bold;
    color: #333333;
  }

  &__logout {
    font-size: 28rpx;
    color: #1890ff;
    padding: 8rpx 16rpx;
  }

  &__refresh {
    text-align: center;
    padding: 24rpx;
  }

  &__refresh-text {
    font-size: 26rpx;
    color: #999999;
  }
}

.dashboard-section {
  margin-bottom: 32rpx;

  &__title {
    font-size: 32rpx;
    font-weight: 500;
    color: #333333;
    margin-bottom: 16rpx;
    padding: 0 24rpx;
  }

  &__content {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 16rpx;
    padding: 0 24rpx;
  }
}

.dashboard-subsection {
  margin-bottom: 24rpx;

  &__title {
    font-size: 28rpx;
    color: #666666;
    margin-bottom: 12rpx;
  }
}
</style>
