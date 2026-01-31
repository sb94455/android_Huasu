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
            <text class="inspection-detail-page__info-label">设备</text>
            <text class="inspection-detail-page__info-value">{{ task.equipment_name || '-' }}</text>
          </view>
          <view class="inspection-detail-page__info-item">
            <text class="inspection-detail-page__info-label">日期</text>
            <text class="inspection-detail-page__info-value">{{ formatDate(task.date) }}</text>
          </view>
          <view class="inspection-detail-page__info-item">
            <text class="inspection-detail-page__info-label">执行人</text>
            <text class="inspection-detail-page__info-value">{{ task.inspector_name || '-' }}</text>
          </view>
          <view class="inspection-detail-page__info-item">
            <text class="inspection-detail-page__info-label">巡检路线</text>
            <text class="inspection-detail-page__info-value">{{ task.route_name || '-' }}</text>
          </view>
        </view>

        <view v-if="task.notes" class="divider"></view>
        <view v-if="task.notes" class="inspection-detail-page__notes">
          <text class="inspection-detail-page__notes-label">备注</text>
          <text class="inspection-detail-page__notes-text">{{ task.notes }}</text>
        </view>
      </view>

      <!-- 点检表单项 -->
      <view class="card">
        <view class="card__title">点检项目</view>
        <view v-if="checklistItems.length === 0" class="empty">
          <text class="empty__text">暂无点检项目</text>
        </view>
        <view v-else class="inspection-detail-page__checklist">
          <view v-for="item in checklistItems" :key="item.id" class="checklist-item">
            <view class="checklist-item__header">
              <text class="checklist-item__name">{{ item.name }}</text>
              <text v-if="item.required" class="checklist-item__required">*</text>
            </view>
            <text v-if="item.description" class="checklist-item__description">{{ item.description }}</text>
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
import { useInspectionStore } from '@/stores/inspection'
import { inspectionApi } from '@/api/inspection'
import { INSPECTION_STATE, INSPECTION_STATE_TEXT, INSPECTION_STATE_COLOR } from '@/utils/constants'

const inspectionStore = useInspectionStore()

const task = ref<any>(null)
const checklistItems = ref<any[]>([])
const loading = ref(false)

const statusText = computed(() => {
  if (!task.value) return ''
  return INSPECTION_STATE_TEXT[task.value.state.toUpperCase() as keyof typeof INSPECTION_STATE] || task.value.state
})

const statusColor = computed(() => {
  if (!task.value) return '#999'
  return INSPECTION_STATE_COLOR[task.value.state.toUpperCase() as keyof typeof INSPECTION_STATE] || '#999'
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
    checklistItems.value = await inspectionApi.getChecklistItems(Number(id))
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
    await inspectionStore.startTask(task.value.id)
    uni.showToast({ title: '开始点检', icon: 'success' })
    // 跳转到提交页面
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

  &__notes {
    margin-top: 16rpx;
  }

  &__notes-label {
    display: block;
    font-size: 24rpx;
    color: #999999;
    margin-bottom: 8rpx;
  }

  &__notes-text {
    display: block;
    font-size: 28rpx;
    color: #333333;
    line-height: 1.6;
  }

  &__checklist {
    display: flex;
    flex-direction: column;
    gap: 16rpx;
  }

  &__actions {
    margin: 32rpx 24rpx 0;
  }
}

.checklist-item {
  padding: 16rpx;
  background: #f5f5f5;
  border-radius: 8rpx;

  &__header {
    display: flex;
    align-items: center;
    margin-bottom: 8rpx;
  }

  &__name {
    font-size: 28rpx;
    color: #333333;
    font-weight: 500;
  }

  &__required {
    color: #ff4d4f;
    margin-left: 8rpx;
  }

  &__description {
    font-size: 26rpx;
    color: #666666;
    line-height: 1.5;
  }
}
</style>
