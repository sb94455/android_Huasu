<template>
  <view class="equipment-card" @click="onClick">
    <view class="equipment-card__header">
      <view class="equipment-card__code">{{ equipment.code }}</view>
      <view class="equipment-card__status" :style="{ backgroundColor: statusColor }">
        <text class="equipment-card__status-text">{{ statusText }}</text>
      </view>
    </view>

    <view class="equipment-card__body">
      <text class="equipment-card__name">{{ equipment.name }}</text>

      <view v-if="equipment.location" class="equipment-card__info">
        <text class="equipment-card__info-text">📍 {{ equipment.location }}</text>
      </view>

      <view v-if="equipment.category" class="equipment-card__info">
        <text class="equipment-card__info-text">🏷️ {{ equipment.category }}</text>
      </view>
    </view>

    <view v-if="showActions" class="equipment-card__footer">
      <view class="equipment-card__action" @click.stop="onDetail">
        <text>详情</text>
      </view>
      <view v-if="canCreateRepair" class="equipment-card__action equipment-card__action--primary" @click.stop="onRepair">
        <text>报修</text>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import type { Equipment } from '@/types'
import { EQUIPMENT_STATUS, EQUIPMENT_STATUS_TEXT, EQUIPMENT_STATUS_COLOR } from '@/utils/constants'

interface Props {
  equipment: Equipment
  showActions?: boolean
  canCreateRepair?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  showActions: true,
  canCreateRepair: true
})

const emit = defineEmits<{
  click: []
  detail: []
  repair: []
}>()

const statusText = computed(() => {
  return EQUIPMENT_STATUS_TEXT[props.equipment.status.toUpperCase() as keyof typeof EQUIPMENT_STATUS] || props.equipment.status
})

const statusColor = computed(() => {
  return EQUIPMENT_STATUS_COLOR[props.equipment.status.toUpperCase() as keyof typeof EQUIPMENT_STATUS] || '#999'
})

function onClick() {
  emit('click')
}

function onDetail() {
  emit('detail')
}

function onRepair() {
  emit('repair')
}
</script>

<style lang="scss" scoped>
.equipment-card {
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

  &__code {
    font-size: 28rpx;
    color: #999999;
    font-family: 'Courier New', monospace;
  }

  &__status {
    padding: 8rpx 16rpx;
    border-radius: 8rpx;
  }

  &__status-text {
    font-size: 24rpx;
    color: #ffffff;
  }

  &__body {
    margin-bottom: 16rpx;
  }

  &__name {
    font-size: 32rpx;
    font-weight: 500;
    color: #333333;
    display: block;
    margin-bottom: 12rpx;
  }

  &__info {
    margin-bottom: 8rpx;
  }

  &__info-text {
    font-size: 26rpx;
    color: #666666;
  }

  &__footer {
    display: flex;
    gap: 16rpx;
    padding-top: 16rpx;
    border-top: 1rpx solid #f0f0f0;
  }

  &__action {
    flex: 1;
    height: 64rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 8rpx;
    border: 1rpx solid #d9d9d9;
    font-size: 28rpx;
    color: #666666;
    transition: all 0.3s;

    &:active {
      opacity: 0.7;
    }

    &--primary {
      background: #1890ff;
      border-color: #1890ff;
      color: #ffffff;
    }
  }
}
</style>
