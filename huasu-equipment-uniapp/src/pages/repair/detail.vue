<template>
  <view class="repair-detail-page">
    <view v-if="loading" class="loading">
      <text>加载中...</text>
    </view>

    <view v-else-if="order" class="container">
      <!-- 报修单信息 -->
      <view class="card">
        <view class="repair-detail-page__header">
          <text class="repair-detail-page__title">{{ order.name }}</text>
          <view class="repair-detail-page__status" :style="{ backgroundColor: statusColor }">
            <text class="repair-detail-page__status-text">{{ statusText }}</text>
          </view>
        </view>

        <view class="divider"></view>

        <view class="repair-detail-page__info">
          <view class="repair-detail-page__info-item">
            <text class="repair-detail-page__info-label">优先级</text>
            <view class="repair-detail-page__priority" :style="{ color: priorityColor }">
              <text class="repair-detail-page__priority-text">{{ priorityText }}</text>
            </view>
          </view>
          <view class="repair-detail-page__info-item">
            <text class="repair-detail-page__info-label">故障类型</text>
            <text class="repair-detail-page__info-value">{{ order.fault_type || '-' }}</text>
          </view>
          <view class="repair-detail-page__info-item">
            <text class="repair-detail-page__info-label">报修日期</text>
            <text class="repair-detail-page__info-value">{{ formatDate(order.create_date) }}</text>
          </view>
        </view>
      </view>

      <!-- 故障描述 -->
      <view class="card">
        <view class="card__title">故障描述</view>
        <text class="repair-detail-page__description">{{ order.fault_description }}</text>
      </view>

      <!-- 操作按钮 -->
      <view class="repair-detail-page__actions">
        <button
          v-if="order.state === 'draft'"
          class="btn btn--primary btn--block btn--large"
          @click="handleSubmit"
        >
          提交报修
        </button>
        <button
          v-if="order.state === 'draft' || order.state === 'submitted'"
          class="btn btn--default btn--block btn--large"
          @click="handleCancel"
        >
          取消报修
        </button>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { repairApi } from '@/api/repair'

const order = ref<any>(null)
const loading = ref(false)

const statusText = computed(() => {
  if (!order.value) return ''
  const stateMap: Record<string, string> = {
    'draft': '草稿',
    'submitted': '已提交',
    'confirmed': '已确认',
    'in_progress': '处理中',
    'done': '已完成',
    'cancelled': '已取消'
  }
  return stateMap[order.value.state] || order.value.state
})

const statusColor = computed(() => {
  if (!order.value) return '#999'
  const colorMap: Record<string, string> = {
    'draft': '#999999',
    'submitted': '#faad14',
    'confirmed': '#1890ff',
    'in_progress': '#1890ff',
    'done': '#52c41a',
    'cancelled': '#ff4d4f'
  }
  return colorMap[order.value.state] || '#999'
})

const priorityText = computed(() => {
  if (!order.value) return ''
  const priorityMap: Record<string, string> = {
    'low': '低',
    'medium': '中',
    'high': '高',
    'urgent': '紧急'
  }
  return priorityMap[order.value.priority] || order.value.priority
})

const priorityColor = computed(() => {
  if (!order.value) return '#999'
  const colorMap: Record<string, string> = {
    'low': '#52c41a',
    'medium': '#faad14',
    'high': '#ff7a45',
    'urgent': '#ff4d4f'
  }
  return colorMap[order.value.priority] || '#999'
})

// 格式化日期
function formatDate(dateStr?: string) {
  if (!dateStr) return '-'
  const date = new Date(dateStr)
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`
}

// 加载报修单详情
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
    order.value = await repairApi.getDetail(Number(id))
  } catch (error: any) {
    uni.showToast({
      title: error.message || '加载失败',
      icon: 'none'
    })
  } finally {
    loading.value = false
  }
}

// 提交报修单
async function handleSubmit() {
  try {
    await repairApi.submit(order.value.id)
    uni.showToast({ title: '提交成功', icon: 'success' })
    setTimeout(() => {
      uni.navigateBack()
    }, 1500)
  } catch (error: any) {
    uni.showToast({
      title: error.message || '提交失败',
      icon: 'none'
    })
  }
}

// 取消报修单
async function handleCancel() {
  uni.showModal({
    title: '确认取消',
    content: '确定要取消此报修单吗？',
    success: async (res) => {
      if (res.confirm) {
        try {
          await repairApi.cancel(order.value.id)
          uni.showToast({ title: '已取消', icon: 'success' })
          setTimeout(() => {
            uni.navigateBack()
          }, 1500)
        } catch (error: any) {
          uni.showToast({
            title: error.message || '取消失败',
            icon: 'none'
          })
        }
      }
    }
  })
}

onMounted(() => {
  loadDetail()
})
</script>

<style lang="scss" scoped>
.repair-detail-page {
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

  &__priority {
    font-size: 28rpx;
    font-weight: 500;
  }

  &__description {
    display: block;
    font-size: 28rpx;
    color: #333333;
    line-height: 1.6;
  }

  &__actions {
    display: flex;
    flex-direction: column;
    gap: 16rpx;
    margin: 32rpx 24rpx 0;
  }
}
</style>
