<template>
  <view class="task-card" @click="onClick">
    <view class="task-card__header">
      <text class="task-card__title">{{ task.name }}</text>
      <view class="task-card__status" :style="{ backgroundColor: statusColor }">
        <text class="task-card__status-text">{{ statusText }}</text>
      </view>
    </view>

    <view class="task-card__info">
      <view v-if="task.equipment_name" class="task-card__info-item">
        <text class="task-card__info-label">设备：</text>
        <text class="task-card__info-value">{{ task.equipment_name }}</text>
      </view>
      <view class="task-card__info-item">
        <text class="task-card__info-label">日期：</text>
        <text class="task-card__info-value">{{ formatDate(task.date) }}</text>
      </view>
    </view>

    <view v-if="task.inspector_name" class="task-card__footer">
      <text class="task-card__inspector">执行人：{{ task.inspector_name }}</text>
    </view>
  </view>
</template>

<script setup lang="ts">
import type { InspectionTask, RepairOrder } from '@/types'
import { INSPECTION_STATE, INSPECTION_STATE_TEXT, INSPECTION_STATE_COLOR } from '@/utils/constants'
import { REPAIR_STATE, REPAIR_STATE_TEXT, REPAIR_STATE_COLOR } from '@/utils/constants'

interface Props {
  task: InspectionTask | RepairOrder
  type: 'inspection' | 'repair'
}

const props = defineProps<Props>()

const emit = defineEmits<{
  click: []
}>()

const statusText = computed(() => {
  if (props.type === 'inspection') {
    return INSPECTION_STATE_TEXT[props.task.state.toUpperCase() as keyof typeof INSPECTION_STATE] || props.task.state
  } else {
    return REPAIR_STATE_TEXT[props.task.state.toUpperCase() as keyof typeof REPAIR_STATE] || props.task.state
  }
})

const statusColor = computed(() => {
  if (props.type === 'inspection') {
    return INSPECTION_STATE_COLOR[props.task.state.toUpperCase() as keyof typeof INSPECTION_STATE] || '#999'
  } else {
    return REPAIR_STATE_COLOR[props.task.state.toUpperCase() as keyof typeof REPAIR_STATE] || '#999'
  }
})

function formatDate(dateStr: string) {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return `${date.getMonth() + 1}/${date.getDate()}`
}

function onClick() {
  emit('click')
}
</script>

<style lang="scss" scoped>
.task-card {
  background: #ffffff;
  border-radius: 16rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);

  &__header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 16rpx;
  }

  &__title {
    flex: 1;
    font-size: 32rpx;
    font-weight: 500;
    color: #333333;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  &__status {
    padding: 8rpx 16rpx;
    border-radius: 8rpx;
    margin-left: 16rpx;
  }

  &__status-text {
    font-size: 24rpx;
    color: #ffffff;
  }

  &__info {
    margin-bottom: 12rpx;
  }

  &__info-item {
    display: flex;
    margin-bottom: 8rpx;
  }

  &__info-label {
    font-size: 26rpx;
    color: #999999;
    margin-right: 8rpx;
  }

  &__info-value {
    font-size: 26rpx;
    color: #666666;
  }

  &__footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-top: 16rpx;
    border-top: 1rpx solid #f0f0f0;
  }

  &__inspector {
    font-size: 26rpx;
    color: #999999;
  }
}
</style>
