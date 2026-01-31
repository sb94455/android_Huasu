<template>
  <view class="repair-create-page">
    <view class="container">
      <!-- 设备选择 -->
      <view class="card">
        <view class="input-group">
          <text class="input-group__label input-group__required">选择设备</text>
          <picker mode="selector" :range="equipmentOptions" range-key="name" @change="onEquipmentChange">
            <view class="picker" :class="{ 'picker--placeholder': !selectedEquipment }">
              {{ selectedEquipment ? selectedEquipment.name : '请选择设备' }}
            </view>
          </picker>
        </view>
      </view>

      <!-- 故障信息 -->
      <view class="card">
        <view class="input-group">
          <text class="input-group__label input-group__required">故障类型</text>
          <picker mode="selector" :range="faultTypes" @change="onFaultTypeChange">
            <view class="picker" :class="{ 'picker--placeholder': !form.fault_type }">
              {{ form.fault_type || '请选择故障类型' }}
            </view>
          </picker>
        </view>

        <view class="input-group">
          <text class="input-group__label input-group__required">优先级</text>
          <picker mode="selector" :range="priorityOptions" range-key="label" @change="onPriorityChange">
            <view class="picker" :class="{ 'picker--placeholder': !form.priority }">
              {{ getPriorityLabel() || '请选择优先级' }}
            </view>
          </picker>
        </view>

        <view class="input-group">
          <text class="input-group__label input-group__required">故障描述</text>
          <textarea
            class="input input--textarea"
            v-model="form.fault_description"
            placeholder="请详细描述故障情况"
            :maxlength="500"
          />
        </view>

        <view class="input-group">
          <text class="input-group__label">备注</text>
          <textarea
            class="input input--textarea"
            v-model="form.notes"
            placeholder="请输入备注信息（可选）"
            :maxlength="200"
          />
        </view>
      </view>

      <!-- 图片上传 -->
      <view class="card">
        <view class="input-group">
          <text class="input-group__label">现场照片</text>
          <view class="repair-create-page__upload">
            <view v-for="(img, index) in images" :key="index" class="upload-item">
              <image class="upload-item__image" :src="img" mode="aspectFill" />
              <view class="upload-item__remove" @click="removeImage(index)">
                <text>×</text>
              </view>
            </view>
            <view v-if="images.length < 9" class="upload-add" @click="chooseImage">
              <text class="upload-add__icon">+</text>
              <text class="upload-add__text">添加图片</text>
            </view>
          </view>
        </view>
      </view>

      <!-- 提交按钮 -->
      <view class="repair-create-page__actions">
        <button class="btn btn--primary btn--block btn--large" :disabled="submitting" @click="handleSubmit">
          {{ submitting ? '提交中...' : '创建报修单' }}
        </button>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRepairStore } from '@/stores/repair'
import { equipmentApi } from '@/api/equipment'
import { FAULT_TYPES, REPAIR_PRIORITY, REPAIR_PRIORITY_TEXT } from '@/utils/constants'

const repairStore = useRepairStore()

const equipmentOptions = ref<any[]>([])
const selectedEquipment = ref<any>(null)
const faultTypes = FAULT_TYPES
const priorityOptions = Object.keys(REPAIR_PRIORITY).map(key => ({
  value: REPAIR_PRIORITY[key as keyof typeof REPAIR_PRIORITY],
  label: REPAIR_PRIORITY_TEXT[REPAIR_PRIORITY[key as keyof typeof REPAIR_PRIORITY] as keyof typeof REPAIR_PRIORITY_TEXT]
}))

const form = ref({
  equipment_id: 0,
  fault_type: '',
  priority: 'medium' as keyof typeof REPAIR_PRIORITY,
  fault_description: '',
  notes: ''
})

const images = ref<string[]>([])
const submitting = ref(false)

// 加载设备列表
async function loadEquipmentList() {
  try {
    const result = await equipmentApi.getList({ limit: 1000 })
    equipmentOptions.value = result.data.filter(e => e.status === 'active')
  } catch (error: any) {
    uni.showToast({
      title: '加载设备列表失败',
      icon: 'none'
    })
  }
}

// 设备选择变更
function onEquipmentChange(e: any) {
  const index = e.detail.value
  selectedEquipment.value = equipmentOptions.value[index]
  form.value.equipment_id = selectedEquipment.value.id
}

// 故障类型变更
function onFaultTypeChange(e: any) {
  form.value.fault_type = faultTypes[e.detail.value]
}

// 优先级变更
function onPriorityChange(e: any) {
  form.value.priority = priorityOptions.value[e.detail.value].value
}

// 获取优先级标签
function getPriorityLabel() {
  if (!form.value.priority) return ''
  return REPAIR_PRIORITY_TEXT[form.value.priority as keyof typeof REPAIR_PRIORITY]
}

// 选择图片
function chooseImage() {
  uni.chooseImage({
    count: 9 - images.value.length,
    sizeType: ['compressed'],
    sourceType: ['album', 'camera'],
    success: (res) => {
      images.value.push(...res.tempFilePaths)
    }
  })
}

// 删除图片
function removeImage(index: number) {
  images.value.splice(index, 1)
}

// 验证表单
function validateForm(): boolean {
  if (!form.value.equipment_id) {
    uni.showToast({ title: '请选择设备', icon: 'none' })
    return false
  }
  if (!form.value.fault_type) {
    uni.showToast({ title: '请选择故障类型', icon: 'none' })
    return false
  }
  if (!form.value.fault_description.trim()) {
    uni.showToast({ title: '请填写故障描述', icon: 'none' })
    return false
  }
  return true
}

// 提交表单
async function handleSubmit() {
  if (!validateForm()) {
    return
  }

  submitting.value = true

  try {
    const id = await repairStore.createRepair({
      equipment_id: form.value.equipment_id,
      fault_type: form.value.fault_type,
      fault_description: form.value.fault_description,
      priority: form.value.priority,
      notes: form.value.notes || undefined,
      images: images.value.length > 0 ? images.value : undefined
    })

    uni.showToast({ title: '创建成功', icon: 'success' })

    setTimeout(() => {
      uni.navigateTo({ url: `/pages/repair/detail?id=${id}` })
    }, 1500)
  } catch (error: any) {
    uni.showToast({
      title: error.message || '创建失败',
      icon: 'none'
    })
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  loadEquipmentList()

  // 检查是否有预选设备
  const pages = getCurrentPages()
  const currentPage = pages[pages.length - 1] as any
  const equipmentId = currentPage.options?.equipmentId

  if (equipmentId) {
    // 预先选择设备
    form.value.equipment_id = Number(equipmentId)
  }
})
</script>

<style lang="scss" scoped>
.repair-create-page {
  min-height: 100vh;
  background: #f5f5f5;
  padding-bottom: 32rpx;

  &__upload {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 16rpx;
  }

  &__actions {
    margin: 32rpx 24rpx 0;
  }
}

.picker {
  height: 80rpx;
  display: flex;
  align-items: center;
  padding: 0 24rpx;
  background: #ffffff;
  border: 1rpx solid #d9d9d9;
  border-radius: 8rpx;
  color: #333333;

  &--placeholder {
    color: #bfbfbf;
  }
}

.upload-item {
  position: relative;
  aspect-ratio: 1;
  border-radius: 8rpx;
  overflow: hidden;

  &__image {
    width: 100%;
    height: 100%;
  }

  &__remove {
    position: absolute;
    top: 8rpx;
    right: 8rpx;
    width: 44rpx;
    height: 44rpx;
    background: rgba(0, 0, 0, 0.6);
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #ffffff;
    font-size: 32rpx;
  }
}

.upload-add {
  aspect-ratio: 1;
  border: 2rpx dashed #d9d9d9;
  border-radius: 8rpx;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 8rpx;

  &__icon {
    font-size: 48rpx;
    color: #999999;
  }

  &__text {
    font-size: 24rpx;
    color: #999999;
  }
}
</style>
