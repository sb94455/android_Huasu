<template>
  <view class="inspection-detail-page">
    <view v-if="loading" class="loading">
      <text>加载中...</text>
    </view>

    <view v-else-if="task" class="container">
      <!-- 任务信息 -->
      <view class="card">
        <view class="inspection-detail-page__header">
          <text class="inspection-detail-page__title">{{ task.name }}</text>
          <view class="inspection-detail-page__status" :style="{ backgroundColor: statusColor }">
            <text class="inspection-detail-page__status-text">{{ statusText }}</text>
          </view>
        </view>

        <view class="divider"></view>

        <view class="inspection-detail-page__info">
          <view class="inspection-detail-page__info-item">
            <text class="inspection-detail-page__info-label">日期</text>
            <text class="inspection-detail-page__info-value">{{ formatDate(task.create_date) }}</text>
          </view>
        </view>
      </view>

      <!-- 操作按钮 -->
      <view class="inspection-detail-page__actions">
        <button
          v-if="task.state === 'pending'"
          class="btn btn--primary btn--block btn--large"
          @click="handleStart"
        >
          开始点检
        </button>
        <button
          v-if="task.state === 'in_progress'"
          class="btn btn--primary btn--block btn--large"
          @click="handleSubmit"
        >
          提交结果
        </button>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { inspectionApi } from '@/api/inspection'

const task = ref<any>(null)
const loading = ref(false)

const statusText = computed(() => {
  if (!task.value) return ''
  const stateMap: Record<string, string> = {
    'draft': '草稿',
    'pending': '待处理',
    'in_progress': '进行中',
    'done': '已完成'
  }
  return stateMap[task.value.state] || task.value.state
})

const statusColor = computed(() => {
  if (!task.value) return '#999'
  const colorMap: Record<string, string> = {
    'draft': '#999999',
    'pending': '#faad14',
    'in_progress': '#1890ff',
    'done': '#52c41a'
  }
  return colorMap[task.value.state] || '#999'
})

// 格式化日期
function formatDate(dateStr?: string) {
  if (!dateStr) return '-'
  const date = new Date(dateStr)
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`
}

// 加载任务详情
async function loadDetail() {
  const pages = getCurrentPages()
  const currentPage = pages[pages.length - 1] as any
  const id = currentPage.options?.id

  if (!id) {
    uni.showToast({ title: '参数错误', icon: 'none' })
    setTimeout(() => uni.navigateBack(), 1500)
    return
  }

  loading.value = true

  try {
    task.value = await inspectionApi.getDetail(Number(id))
  } catch (error: any) {
    uni.showToast({
      title: error.message || '加载失败',
      icon: 'none'
    })
  } finally {
    loading.value = false
  }
}

// 开始点检
async function handleStart() {
  try {
    await inspectionApi.startTask(task.value.id)
    uni.showToast({ title: '开始点检', icon: 'success' })
    uni.navigateTo({ url: `/pages/inspection/submit?id=${task.value.id}` })
  } catch (error: any) {
    uni.showToast({
      title: error.message || '操作失败',
      icon: 'none'
    })
  }
}

// 提交结果
function handleSubmit() {
  uni.navigateTo({ url: `/pages/inspection/submit?id=${task.value.id}` })
}

onMounted(() => {
  loadDetail()
})
</script>

<style lang="scss" scoped>
.inspection-detail-page {
  min-height: 100vh;
  background: #f5f5f5;
  padding-bottom: 32rpx;

  &__header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
  }

  &__title {
    flex: 1;
    font-size: 32rpx;
    font-weight: 500;
    color: #333333;
  }

  &__status {
    padding: 12rpx 24rpx;
    border-radius: 8rpx;
    margin-left: 16rpx;
  }

  &__status-text {
    font-size: 24rpx;
    color: #ffffff;
  }

  &__info {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 24rpx;
  }

  &__info-item {
    display: flex;
    flex-direction: column;
  }

  &__info-label {
    font-size: 24rpx;
    color: #999999;
    margin-bottom: 8rpx;
  }

  &__info-value {
    font-size: 28rpx;
    color: #333333;
  }

  &__actions {
    margin: 32rpx 24rpx 0;
  }
}
</style>
