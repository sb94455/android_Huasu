<template>
  <view class="equipment-card" @click="onClick">
    <view class="equipment-card__header">
      <view class="equipment-card__main">
        <text class="equipment-card__code">{{ equipment.name || '-' }}</text>
        <text class="equipment-card__name">{{ equipment.equipment_name }}</text>
      </view>
      <view class="equipment-card__status" :style="{ backgroundColor: statusColor }">
        <text class="equipment-card__status-text">{{ statusText }}</text>
      </view>
    </view>

    <view class="equipment-card__info" v-if="equipment.model || equipment.brand_id || equipment.location_id || equipment.equipment_type">
      <view class="equipment-card__info-item" v-if="equipment.model">
        <text class="equipment-card__info-label">型号</text>
        <text class="equipment-card__info-value">{{ equipment.model }}</text>
      </view>
      <view class="equipment-card__info-item" v-if="equipment.brand_id">
        <text class="equipment-card__info-label">品牌</text>
        <text class="equipment-card__info-value">{{ getBrandName(equipment.brand_id) }}</text>
      </view>
      <view class="equipment-card__info-item" v-if="equipment.equipment_type">
        <text class="equipment-card__info-label">类型</text>
        <text class="equipment-card__info-value">{{ getEquipmentTypeLabel(equipment.equipment_type) }}</text>
      </view>
      <view class="equipment-card__info-item" v-if="equipment.location_id">
        <text class="equipment-card__info-label">位置</text>
        <text class="equipment-card__info-value">{{ getLocationName(equipment.location_id) }}</text>
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
import type { Equipment, EquipmentType } from '@/types'

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

// 状态映射：normal(正常), bug(异常), maintenance(维修中), scrapped(已报废)
const statusText = computed(() => {
  const stateMap: Record<string, string> = {
    'normal': '正常',
    'bug': '异常',
    'maintenance': '维修中',
    'scrapped': '已报废'
  }
  return stateMap[props.equipment.state] || props.equipment.state
})

const statusColor = computed(() => {
  const colorMap: Record<string, string> = {
    'normal': '#52c41a',     // 正常 - 绿色
    'bug': '#ff4d4f',        // 异常 - 红色
    'maintenance': '#faad14', // 维修中 - 橙色
    'scrapped': '#999999'     // 已报废 - 灰色
  }
  return colorMap[props.equipment.state] || '#999'
})

// 获取品牌名称
function getBrandName(brandId: number | Array<[number, string]>): string {
  if (Array.isArray(brandId)) {
    return brandId[1] || '-'
  }
  return '-'
}

// 获取位置名称
function getLocationName(locationId: number | Array<[number, string]>): string {
  if (Array.isArray(locationId)) {
    return locationId[1] || '-'
  }
  return '-'
}

// 获取设备类型标签
function getEquipmentTypeLabel(type: EquipmentType): string {
  const typeMap: Record<EquipmentType, string> = {
    'production': '生产设备',
    'office': '办公设备',
    'testing': '检测设备',
    'other': '其他设备'
  }
  return typeMap[type] || type
}

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
    align-items: flex-start;
    margin-bottom: 12rpx;
  }

  &__main {
    flex: 1;
    margin-right: 16rpx;
  }

  &__code {
    display: block;
    font-size: 24rpx;
    color: #999999;
    font-family: 'Courier New', monospace;
    margin-bottom: 4rpx;
  }

  &__name {
    display: block;
    font-size: 30rpx;
    font-weight: 500;
    color: #333333;
  }

  &__status {
    padding: 8rpx 16rpx;
    border-radius: 8rpx;
    flex-shrink: 0;
  }

  &__status-text {
    font-size: 24rpx;
    color: #ffffff;
  }

  &__info {
    display: flex;
    flex-wrap: wrap;
    gap: 16rpx 24rpx;
    margin-bottom: 12rpx;
  }

  &__info-item {
    display: flex;
    align-items: baseline;
    font-size: 24rpx;
  }

  &__info-label {
    color: #999999;
    margin-right: 8rpx;
  }

  &__info-value {
    color: #333333;
    font-weight: 400;
  }

  &__footer {
    display: flex;
    gap: 16rpx;
    padding-top: 12rpx;
    border-top: 1rpx solid #f0f0f0;
  }

  &__action {
    flex: 1;
    height: 56rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 8rpx;
    border: 1rpx solid #d9d9d9;
    font-size: 26rpx;
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
