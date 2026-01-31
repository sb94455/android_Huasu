<template>
  <view class="equipment-detail-page">
    <view v-if="loading" class="loading">
      <text>加载中...</text>
    </view>

    <view v-else-if="equipment" class="container">
      <!-- 设备基本信息 -->
      <view class="card">
        <view class="equipment-detail-page__header">
          <view>
            <text class="equipment-detail-page__name">{{ equipment.name }}</text>
            <text class="equipment-detail-page__code">{{ equipment.code }}</text>
          </view>
          <view class="equipment-detail-page__status" :style="{ backgroundColor: statusColor }">
            <text class="equipment-detail-page__status-text">{{ statusText }}</text>
          </view>
        </view>

        <view class="divider"></view>

        <view class="equipment-detail-page__info">
          <view class="equipment-detail-page__info-item">
            <text class="equipment-detail-page__info-label">分类</text>
            <text class="equipment-detail-page__info-value">{{ equipment.category || '-' }}</text>
          </view>
          <view class="equipment-detail-page__info-item">
            <text class="equipment-detail-page__info-label">位置</text>
            <text class="equipment-detail-page__info-value">{{ equipment.location || '-' }}</text>
          </view>
          <view class="equipment-detail-page__info-item">
            <text class="equipment-detail-page__info-label">型号</text>
            <text class="equipment-detail-page__info-value">{{ equipment.model || '-' }}</text>
          </view>
          <view class="equipment-detail-page__info-item">
            <text class="equipment-detail-page__info-label">制造商</text>
            <text class="equipment-detail-page__info-value">{{ equipment.manufacturer || '-' }}</text>
          </view>
          <view class="equipment-detail-page__info-item">
            <text class="equipment-detail-page__info-label">购买日期</text>
            <text class="equipment-detail-page__info-value">{{ formatDate(equipment.purchase_date) }}</text>
          </view>
          <view class="equipment-detail-page__info-item">
            <text class="equipment-detail-page__info-label">保修到期</text>
            <text class="equipment-detail-page__info-value">{{ formatDate(equipment.warranty_expiry) }}</text>
          </view>
        </view>

        <view v-if="equipment.notes" class="divider"></view>
        <view v-if="equipment.notes" class="equipment-detail-page__notes">
          <text class="equipment-detail-page__notes-label">备注</text>
          <text class="equipment-detail-page__notes-text">{{ equipment.notes }}</text>
        </view>
      </view>

      <!-- 维修历史 -->
      <view class="card">
        <view class="card__title">维修历史</view>
        <view v-if="repairHistory.length === 0" class="empty">
          <text class="empty__text">暂无维修记录</text>
        </view>
        <view v-else class="equipment-detail-page__history">
          <view v-for="item in repairHistory" :key="item.id" class="history-item">
            <text class="history-item__date">{{ formatDate(item.date) }}</text>
            <text class="history-item__fault">{{ item.fault_type || '故障' }}</text>
            <view class="history-item__status tag tag--default">{{ item.state }}</view>
          </view>
        </view>
      </view>

      <!-- 点检历史 -->
      <view class="card">
        <view class="card__title">点检历史</view>
        <view v-if="inspectionHistory.length === 0" class="empty">
          <text class="empty__text">暂无点检记录</text>
        </view>
        <view v-else class="equipment-detail-page__history">
          <view v-for="item in inspectionHistory" :key="item.id" class="history-item">
            <text class="history-item__date">{{ formatDate(item.date) }}</text>
            <text class="history-item__task">{{ item.name }}</text>
            <view class="history-item__status" :class="getInspectionStatusClass(item.state)">
              {{ item.state }}
            </view>
          </view>
        </view>
      </view>

      <!-- 操作按钮 -->
      <view class="equipment-detail-page__actions">
        <button class="btn btn--primary btn--block btn--large" @click="handleCreateRepair">
          创建报修
        </button>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useEquipmentStore } from '@/stores/equipment'
import { equipmentApi } from '@/api/equipment'
import { EQUIPMENT_STATUS, EQUIPMENT_STATUS_TEXT, EQUIPMENT_STATUS_COLOR } from '@/utils/constants'

const equipmentStore = useEquipmentStore()

const equipment = ref<any>(null)
const repairHistory = ref<any[]>([])
const inspectionHistory = ref<any[]>([])
const loading = ref(false)

const statusText = computed(() => {
  if (!equipment.value) return ''
  return EQUIPMENT_STATUS_TEXT[equipment.value.status.toUpperCase() as keyof typeof EQUIPMENT_STATUS] || equipment.value.status
})

const statusColor = computed(() => {
  if (!equipment.value) return '#999'
  return EQUIPMENT_STATUS_COLOR[equipment.value.status.toUpperCase() as keyof typeof EQUIPMENT_STATUS] || '#999'
})

// 格式化日期
function formatDate(dateStr?: string) {
  if (!dateStr) return '-'
  const date = new Date(dateStr)
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`
}

// 获取点检状态样式
function getInspectionStatusClass(state: string) {
  const classMap: Record<string, string> = {
    'done': 'tag tag--success',
    'pending': 'tag tag--warning',
    'in_progress': 'tag tag--primary',
    'draft': 'tag tag--default'
  }
  return classMap[state] || 'tag tag--default'
}

// 加载设备详情
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
    equipment.value = await equipmentApi.getDetail(Number(id))

    // 加载维修历史
    repairHistory.value = await equipmentApi.getRepairHistory(Number(id))

    // 加载点检历史
    inspectionHistory.value = await equipmentApi.getInspectionHistory(Number(id))
  } catch (error: any) {
    uni.showToast({
      title: error.message || '加载失败',
      icon: 'none'
    })
  } finally {
    loading.value = false
  }
}

// 创建报修
function handleCreateRepair() {
  uni.navigateTo({ url: `/pages/repair/create?equipmentId=${equipment.value.id}` })
}

onMounted(() => {
  loadDetail()
})
</script>

<style lang="scss" scoped>
.equipment-detail-page {
  min-height: 100vh;
  background: #f5f5f5;
  padding-bottom: 32rpx;

  &__header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
  }

  &__name {
    display: block;
    font-size: 36rpx;
    font-weight: 500;
    color: #333333;
    margin-bottom: 8rpx;
  }

  &__code {
    display: block;
    font-size: 26rpx;
    color: #999999;
    font-family: 'Courier New', monospace;
  }

  &__status {
    padding: 12rpx 24rpx;
    border-radius: 8rpx;
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

  &__history {
    display: flex;
    flex-direction: column;
    gap: 16rpx;
  }

  &__actions {
    margin: 32rpx 24rpx 0;
  }
}

.history-item {
  display: flex;
  align-items: center;
  padding: 16rpx;
  background: #f5f5f5;
  border-radius: 8rpx;

  &__date {
    font-size: 26rpx;
    color: #666666;
    width: 180rpx;
  }

  &__fault,
  &__task {
    flex: 1;
    font-size: 28rpx;
    color: #333333;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  &__status {
    font-size: 24rpx;
  }
}
</style>
