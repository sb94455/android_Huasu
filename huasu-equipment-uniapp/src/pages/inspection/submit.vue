<template>
  <view class="inspection-submit-page">
    <view v-if="loading" class="loading">
      <text>加载中...</text>
    </view>

    <view v-else class="container">
      <!-- 任务信息 -->
      <view class="card">
        <view class="card__title">{{ task?.name }}</view>
        <view class="inspection-submit-page__info">
          <text class="inspection-submit-page__info-text">设备：{{ task?.equipment_name || '-' }}</text>
        </view>
      </view>

      <!-- 点检表单 -->
      <view class="card">
        <view class="card__title">点检结果</view>
        <view v-if="checklistItems.length === 0" class="empty">
          <text class="empty__text">暂无点检项目</text>
        </view>
        <view v-else class="inspection-submit-page__form">
          <view v-for="item in checklistItems" :key="item.id" class="form-item">
            <view class="form-item__label">
              <text>{{ item.name }}</text>
              <text v-if="item.required" class="form-item__required">*</text>
            </view>
            <text v-if="item.description" class="form-item__description">{{ item.description }}</text>

            <!-- 布尔类型 -->
            <radio-group v-if="item.type === 'boolean'" class="form-item__radio" @change="onBooleanChange(item.id, $event)">
              <label class="form-item__radio-option">
                <radio value="true" :checked="formData[item.id] === true" />
                <text>正常</text>
              </label>
              <label class="form-item__radio-option">
                <radio value="false" :checked="formData[item.id] === false" />
                <text>异常</text>
              </label>
            </radio-group>

            <!-- 数字类型 -->
            <input
              v-else-if="item.type === 'numeric'"
              class="input"
              type="digit"
              :value="formData[item.id]"
              @input="onNumericChange(item.id, $event)"
            />

            <!-- 文本类型 -->
            <textarea
              v-else-if="item.type === 'text'"
              class="input input--textarea"
              :value="formData[item.id]"
              @input="onTextChange(item.id, $event)"
            />

            <!-- 选择类型 -->
            <picker
              v-else-if="item.type === 'select'"
              mode="selector"
              :range="item.options || []"
              @change="onSelectChange(item.id, $event)"
            >
              <view class="picker">
                {{ formData[item.id] || '请选择' }}
              </view>
            </picker>
          </view>
        </view>
      </view>

      <!-- 备注 -->
      <view class="card">
        <view class="input-group">
          <text class="input-group__label">备注</text>
          <textarea
            class="input input--textarea"
            v-model="notes"
            placeholder="请输入备注信息（可选）"
            :maxlength="500"
          />
        </view>
      </view>

      <!-- 提交按钮 -->
      <view class="inspection-submit-page__actions">
        <button class="btn btn--primary btn--block btn--large" :disabled="submitting" @click="handleSubmit">
          {{ submitting ? '提交中...' : '提交结果' }}
        </button>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useInspectionStore } from '@/stores/inspection'
import { inspectionApi } from '@/api/inspection'

const inspectionStore = useInspectionStore()

const task = ref<any>(null)
const checklistItems = ref<any[]>([])
const formData = ref<Record<number, any>>({})
const notes = ref('')
const loading = ref(false)
const submitting = ref(false)

// 布尔值变更
function onBooleanChange(itemId: number, e: any) {
  formData.value[itemId] = e.detail.value === 'true'
}

// 数字值变更
function onNumericChange(itemId: number, e: any) {
  formData.value[itemId] = Number(e.detail.value)
}

// 文本值变更
function onTextChange(itemId: number, e: any) {
  formData.value[itemId] = e.detail.value
}

// 选择值变更
function onSelectChange(itemId: number, e: any) {
  const item = checklistItems.value.find(i => i.id === itemId)
  if (item && item.options) {
    formData.value[itemId] = item.options[e.detail.value]
  }
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

// 验证表单
function validateForm(): boolean {
  for (const item of checklistItems.value) {
    if (item.required && formData.value[item.id] === undefined) {
      uni.showToast({
        title: `请填写：${item.name}`,
        icon: 'none'
      })
      return false
    }
  }
  return true
}

// 提交结果
async function handleSubmit() {
  if (!validateForm()) {
    return
  }

  submitting.value = true

  try {
    const items = checklistItems.value.map(item => ({
      checklist_item_id: item.id,
      value: formData.value[item.id],
      notes: ''
    }))

    await inspectionStore.submitResult(task.value.id, {
      task_id: task.value.id,
      items,
      notes: notes.value
    })

    uni.showToast({ title: '提交成功', icon: 'success' })

    setTimeout(() => {
      uni.navigateBack()
    }, 1500)
  } catch (error: any) {
    uni.showToast({
      title: error.message || '提交失败',
      icon: 'none'
    })
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  loadDetail()
})
</script>

<style lang="scss" scoped>
.inspection-submit-page {
  min-height: 100vh;
  background: #f5f5f5;
  padding-bottom: 32rpx;

  &__info {
    padding: 16rpx;
    background: #f5f5f5;
    border-radius: 8rpx;
  }

  &__info-text {
    font-size: 26rpx;
    color: #666666;
  }

  &__form {
    display: flex;
    flex-direction: column;
    gap: 32rpx;
  }

  &__actions {
    margin: 32rpx 24rpx 0;
  }
}

.form-item {
  &__label {
    display: flex;
    align-items: center;
    font-size: 28rpx;
    color: #333333;
    font-weight: 500;
    margin-bottom: 12rpx;
  }

  &__required {
    color: #ff4d4f;
    margin-left: 4rpx;
  }

  &__description {
    display: block;
    font-size: 26rpx;
    color: #999999;
    margin-bottom: 12rpx;
  }

  &__radio {
    display: flex;
    gap: 32rpx;
  }

  &__radio-option {
    display: flex;
    align-items: center;
    gap: 8rpx;
  }
}

.input {
  &--textarea {
    min-height: 120rpx;
    padding: 16rpx;
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
}
</style>
