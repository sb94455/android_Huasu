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
            <text class="equipment-detail-page__code">{{ equipment.name }}</text>
            <text class="equipment-detail-page__name">{{ equipment.equipment_name }}</text>
          </view>
          <view class="equipment-detail-page__status" :style="{ backgroundColor: statusColor }">
            <text class="equipment-detail-page__status-text">{{ statusText }}</text>
          </view>
        </view>

        <view class="divider"></view>

        <view class="equipment-detail-page__info">
          <view class="equipment-detail-page__info-item" v-if="equipment.model">
            <text class="equipment-detail-page__info-label">型号</text>
            <text class="equipment-detail-page__info-value">{{ equipment.model || '-' }}</text>
          </view>
          <view class="equipment-detail-page__info-item" v-if="equipment.brand_id">
            <text class="equipment-detail-page__info-label">品牌</text>
            <text class="equipment-detail-page__info-value">{{ getBrandName(equipment.brand_id) }}</text>
          </view>
          <view class="equipment-detail-page__info-item" v-if="equipment.equipment_type">
            <text class="equipment-detail-page__info-label">类型</text>
            <text class="equipment-detail-page__info-value">{{ getEquipmentTypeLabel(equipment.equipment_type) }}</text>
          </view>
          <view class="equipment-detail-page__info-item" v-if="equipment.level">
            <text class="equipment-detail-page__info-label">等级</text>
            <text class="equipment-detail-page__info-value">{{ getLevelLabel(equipment.level) }}</text>
          </view>
          <view class="equipment-detail-page__info-item" v-if="equipment.location_id">
            <text class="equipment-detail-page__info-label">位置</text>
            <text class="equipment-detail-page__info-value">{{ getLocationName(equipment.location_id) }}</text>
          </view>
          <view class="equipment-detail-page__info-item" v-if="equipment.location">
            <text class="equipment-detail-page__info-label">位置号</text>
            <text class="equipment-detail-page__info-value">{{ equipment.location }}</text>
          </view>
          <view class="equipment-detail-page__info-item" v-if="equipment.storage_location">
            <text class="equipment-detail-page__info-label">存放位置</text>
            <text class="equipment-detail-page__info-value">{{ equipment.storage_location }}</text>
          </view>
          <view class="equipment-detail-page__info-item" v-if="equipment.department_id">
            <text class="equipment-detail-page__info-label">所属部门</text>
            <text class="equipment-detail-page__info-value">{{ getDepartmentName(equipment.department_id) }}</text>
          </view>
          <view class="equipment-detail-page__info-item" v-if="equipment.responsible_id">
            <text class="equipment-detail-page__info-label">负责人</text>
            <text class="equipment-detail-page__info-value">{{ getResponsibleName(equipment.responsible_id) }}</text>
          </view>
          <view class="equipment-detail-page__info-item" v-if="equipment.factory_code">
            <text class="equipment-detail-page__info-label">出厂编号</text>
            <text class="equipment-detail-page__info-value">{{ equipment.factory_code }}</text>
          </view>
          <view class="equipment-detail-page__info-item" v-if="equipment.asset_code">
            <text class="equipment-detail-page__info-label">资产编号</text>
            <text class="equipment-detail-page__info-value">{{ equipment.asset_code }}</text>
          </view>
          <view class="equipment-detail-page__info-item" v-if="equipment.purchase_date">
            <text class="equipment-detail-page__info-label">购置日期</text>
            <text class="equipment-detail-page__info-value">{{ formatDate(equipment.purchase_date) }}</text>
          </view>
          <view class="equipment-detail-page__info-item" v-if="equipment.purchase_amount">
            <text class="equipment-detail-page__info-label">购置金额</text>
            <text class="equipment-detail-page__info-value">¥{{ equipment.purchase_amount }}</text>
          </view>
          <view class="equipment-detail-page__info-item" v-if="equipment.warranty_period">
            <text class="equipment-detail-page__info-label">保修期</text>
            <text class="equipment-detail-page__info-value">{{ equipment.warranty_period }} 个月</text>
          </view>
          <view class="equipment-detail-page__info-item" v-if="equipment.warranty_end_date">
            <text class="equipment-detail-page__info-label">保修到期日</text>
            <text class="equipment-detail-page__info-value">{{ formatDate(equipment.warranty_end_date) }}</text>
          </view>
          <view class="equipment-detail-page__info-item" v-if="equipment.warranty_status">
            <text class="equipment-detail-page__info-label">保修状态</text>
            <text class="equipment-detail-page__info-value">{{ getWarrantyStatusLabel(equipment.warranty_status) }}</text>
          </view>
        </view>

        <view v-if="equipment.notes" class="divider"></view>
        <view v-if="equipment.notes" class="equipment-detail-page__notes">
          <text class="equipment-detail-page__notes-label">备注</text>
          <text class="equipment-detail-page__notes-text">{{ equipment.notes }}</text>
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
import { equipmentApi } from '@/api/equipment'
import type { Equipment, EquipmentType, EquipmentLevel, WarrantyStatus } from '@/types'

const equipment = ref<Equipment | null>(null)
const loading = ref(false)

// 状态映射：normal(正常), bug(异常), maintenance(维修中), scrapped(已报废)
const statusText = computed(() => {
  const stateMap: Record<string, string> = {
    'normal': '正常',
    'bug': '异常',
    'maintenance': '维修中',
    'scrapped': '已报废'
  }
  return stateMap[equipment.value?.state || ''] || equipment.value?.state || ''
})

const statusColor = computed(() => {
  const colorMap: Record<string, string> = {
    'normal': '#52c41a',     // 正常 - 绿色
    'bug': '#ff4d4f',        // 异常 - 红色
    'maintenance': '#faad14', // 维修中 - 橙色
    'scrapped': '#999999'     // 已报废 - 灰色
  }
  return colorMap[equipment.value?.state || ''] || '#999'
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

// 获取部门名称
function getDepartmentName(departmentId: number | Array<[number, string]>): string {
  if (Array.isArray(departmentId)) {
    return departmentId[1] || '-'
  }
  return '-'
}

// 获取负责人名称
function getResponsibleName(responsibleId: number | Array<[number, string]>): string {
  if (Array.isArray(responsibleId)) {
    return responsibleId[1] || '-'
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

// 获取等级标签
function getLevelLabel(level: EquipmentLevel): string {
  const levelMap: Record<EquipmentLevel, string> = {
    'a': 'A类',
    'b': 'B类',
    'c': 'C类'
  }
  return levelMap[level] || level
}

// 获取保修状态标签
function getWarrantyStatusLabel(status: WarrantyStatus): string {
  const statusMap: Record<WarrantyStatus, string> = {
    'in_warranty': '保修期内',
    'out_of_warranty': '已过保修期',
    'no_warranty': '无保修信息'
  }
  return statusMap[status] || status
}

// 格式化日期
function formatDate(dateStr?: string) {
  if (!dateStr) return '-'
  const date = new Date(dateStr)
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`
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
  uni.navigateTo({ url: `/pages/repair/create?equipmentId=${equipment.value?.id}` })
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

  &__code {
    display: block;
    font-size: 24rpx;
    color: #999999;
    font-family: 'Courier New', monospace;
    margin-bottom: 6rpx;
  }

  &__name {
    display: block;
    font-size: 36rpx;
    font-weight: 500;
    color: #333333;
  }

  &__status {
    padding: 12rpx 24rpx;
    border-radius: 8rpx;
    flex-shrink: 0;
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

  &__actions {
    margin: 32rpx 24rpx 0;
  }
}
</style>
