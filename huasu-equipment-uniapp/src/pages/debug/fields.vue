<template>
  <view class="debug-fields-page">
    <view class="container">
      <view class="card">
        <text class="debug-fields-page__title">模型字段检测</text>
        <text class="debug-fields-page__model">模型: maintenance.equipment.re</text>

        <button class="btn btn--primary btn--block" @click="loadFields">
          获取可用字段列表
        </button>

        <view v-if="loading" class="debug-fields-page__loading">
          <text>加载中...</text>
        </view>

        <view v-else-if="fields" class="debug-fields-page__result">
          <view class="debug-fields-page__result-header">
            <text>可用字段 ({{ Object.keys(fields).length }} 个)</text>
          </view>
          <view class="debug-fields-page__field-list">
            <view
              v-for="(field, name) in fields"
              :key="name"
              class="debug-fields-page__field-item"
            >
              <text class="debug-fields-page__field-name">{{ name }}</text>
              <text class="debug-fields-page__field-type">{{ field.type }}</text>
              <text class="debug-fields-page__field-label">{{ field.string }}</text>
            </view>
          </view>
        </view>

        <view v-if="error" class="debug-fields-page__error">
          <text class="debug-fields-page__error-title">错误信息:</text>
          <text class="debug-fields-page__error-message">{{ error }}</text>
        </view>
      </view>

      <view class="card">
        <text class="debug-fields-page__subtitle">配置文件位置</text>
        <text class="debug-fields-page__path">src/config/fields.ts</text>
        <text class="debug-fields-page__hint">根据上方的字段列表，更新配置文件中的字段名</text>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { equipmentApi } from '@/api/equipment'

const fields = ref<Record<string, any> | null>(null)
const loading = ref(false)
const error = ref('')

async function loadFields() {
  loading.value = true
  error.value = ''

  try {
    fields.value = await equipmentApi.getFields()
    console.log('可用字段:', fields.value)
  } catch (e: any) {
    error.value = e.message || '加载失败'
    console.error('加载字段失败:', e)
  } finally {
    loading.value = false
  }
}
</script>

<style lang="scss" scoped>
.debug-fields-page {
  min-height: 100vh;
  background: #f5f5f5;
  padding: 24rpx;

  &__title {
    display: block;
    font-size: 36rpx;
    font-weight: 500;
    color: #333333;
    margin-bottom: 8rpx;
  }

  &__model {
    display: block;
    font-size: 24rpx;
    color: #999999;
    font-family: 'Courier New', monospace;
    margin-bottom: 24rpx;
  }

  &__loading {
    text-align: center;
    padding: 48rpx;
    color: #999999;
  }

  &__result {
    margin-top: 24rpx;
  }

  &__result-header {
    font-size: 28rpx;
    font-weight: 500;
    color: #333333;
    margin-bottom: 16rpx;
    padding-bottom: 12rpx;
    border-bottom: 1rpx solid #e8e8e8;
  }

  &__field-list {
    max-height: 800rpx;
    overflow-y: auto;
  }

  &__field-item {
    display: flex;
    align-items: center;
    padding: 12rpx 16rpx;
    background: #fafafa;
    border-radius: 8rpx;
    margin-bottom: 8rpx;
  }

  &__field-name {
    flex: 0 0 200rpx;
    font-size: 24rpx;
    color: #1890ff;
    font-family: 'Courier New', monospace;
    font-weight: 500;
  }

  &__field-type {
    flex: 0 0 100rpx;
    font-size: 22rpx;
    color: #52c41a;
    font-family: 'Courier New', monospace;
  }

  &__field-label {
    flex: 1;
    font-size: 26rpx;
    color: #333333;
  }

  &__error {
    margin-top: 24rpx;
    padding: 16rpx;
    background: #fff2f0;
    border: 1rpx solid #ffccc7;
    border-radius: 8rpx;
  }

  &__error-title {
    display: block;
    font-size: 24rpx;
    color: #ff4d4f;
    font-weight: 500;
    margin-bottom: 8rpx;
  }

  &__error-message {
    display: block;
    font-size: 24rpx;
    color: #666666;
  }

  &__subtitle {
    display: block;
    font-size: 28rpx;
    font-weight: 500;
    color: #333333;
    margin-bottom: 8rpx;
  }

  &__path {
    display: block;
    font-size: 24rpx;
    color: #1890ff;
    font-family: 'Courier New', monospace;
    margin-bottom: 8rpx;
  }

  &__hint {
    display: block;
    font-size: 24rpx;
    color: #999999;
    line-height: 1.6;
  }
}
</style>
