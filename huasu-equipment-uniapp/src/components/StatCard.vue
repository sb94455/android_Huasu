<template>
  <view class="stat-card" :class="{ 'stat-card--clickable': clickable }" @click="onClick">
    <view class="stat-card__icon" :style="{ backgroundColor: iconBg }">
      <text class="stat-card__icon-text">{{ icon }}</text>
    </view>
    <view class="stat-card__content">
      <text class="stat-card__value">{{ value }}</text>
      <text class="stat-card__label">{{ label }}</text>
    </view>
    <view v-if="trend" class="stat-card__trend" :class="trendClass">
      <text>{{ trend }}</text>
    </view>
  </view>
</template>

<script setup lang="ts">
interface Props {
  icon: string
  iconBg?: string
  value: number | string
  label: string
  trend?: string
  trendUp?: boolean
  clickable?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  iconBg: '#1890ff',
  trend: '',
  trendUp: true,
  clickable: false
})

const emit = defineEmits<{
  click: []
}>()

const trendClass = computed(() => ({
  'stat-card__trend--up': props.trendUp,
  'stat-card__trend--down': !props.trendUp
}))

function onClick() {
  if (props.clickable) {
    emit('click')
  }
}
</script>

<style lang="scss" scoped>
.stat-card {
  display: flex;
  align-items: center;
  padding: 24rpx;
  background: #ffffff;
  border-radius: 16rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);

  &--clickable {
    cursor: pointer;
    transition: all 0.3s;

    &:active {
      transform: scale(0.98);
      opacity: 0.8;
    }
  }

  &__icon {
    width: 88rpx;
    height: 88rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 16rpx;
    margin-right: 24rpx;
  }

  &__icon-text {
    font-size: 44rpx;
  }

  &__content {
    flex: 1;
    display: flex;
    flex-direction: column;
  }

  &__value {
    font-size: 40rpx;
    font-weight: bold;
    color: #333333;
    line-height: 1.2;
  }

  &__label {
    font-size: 26rpx;
    color: #999999;
    margin-top: 8rpx;
  }

  &__trend {
    font-size: 24rpx;
    padding: 4rpx 12rpx;
    border-radius: 8rpx;

    &--up {
      color: #52c41a;
      background: rgba(82, 196, 26, 0.1);
    }

    &--down {
      color: #ff4d4f;
      background: rgba(255, 77, 79, 0.1);
    }
  }
}
</style>
