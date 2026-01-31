<template>
  <view class="repair-list-page">
    <!-- 筛选标签 -->
    <view class="repair-list-page__filter">
      <view
        v-for="item in filterOptions"
        :key="item.value"
        class="filter-item"
        :class="{ 'filter-item--active': selectedFilter === item.value }"
        @click="handleFilterChange(item.value)"
      >
        <text class="filter-item__text">{{ item.label }}</text>
      </view>
    </view>

    <!-- 报修单列表 -->
    <view class="repair-list-page__content">
      <view v-if="loading" class="loading">
        <text>加载中...</text>
      </view>

      <view v-else-if="filteredList.length === 0" class="empty">
        <text class="empty__icon">🔴</text>
        <text class="empty__text">暂无报修单</text>
      </view>

      <view v-else class="repair-list">
        <task-card
          v-for="order in filteredList"
          :key="order.id"
          :task="order"
          type="repair"
          @click="viewDetail(order.id)"
        />
      </view>

      <!-- 加载更多 -->
      <view v-if="repairStore.hasMore && !loading" class="load-more" @click="loadMore">
        <text>加载更多</text>
      </view>
    </view>

    <!-- 悬浮按钮 -->
    <view class="repair-list-page__fab" @click="createRepair">
      <text class="repair-list-page__fab-text">+</text>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRepairStore } from '@/stores/repair'

const repairStore = useRepairStore()

const filterOptions = [
  { label: '全部', value: 'all' },
  { label: '待处理', value: 'submitted' },
  { label: '处理中', value: 'in_progress' },
  { label: '已完成', value: 'done' }
]

const selectedFilter = ref('all')
const loading = ref(false)

const filteredList = computed(() => {
  if (selectedFilter.value === 'all') {
    return repairStore.list
  }
  return repairStore.list.filter(order => order.state === selectedFilter.value)
})

// 加载报修单列表
async function loadList(refresh = false) {
  if (loading.value) return

  loading.value = true

  try {
    const page = refresh ? 1 : repairStore.currentPage + 1
    await repairStore.fetchList({ page, limit: 20 }, refresh)
  } catch (error: any) {
    uni.showToast({
      title: error.message || '加载失败',
      icon: 'none'
    })
  } finally {
    loading.value = false
  }
}

// 切换筛选
function handleFilterChange(value: string) {
  selectedFilter.value = value
  repairStore.setFilter(value)
}

// 加载更多
async function loadMore() {
  await loadList(false)
}

// 查看详情
function viewDetail(id: number) {
  uni.navigateTo({ url: `/pages/repair/detail?id=${id}` })
}

// 创建报修
function createRepair() {
  uni.navigateTo({ url: '/pages/repair/create' })
}

// 下拉刷新
onPullDownRefresh(() => {
  repairStore.clearList()
  loadList(true).finally(() => {
    uni.stopPullDownRefresh()
  })
})

// 上拉加载
onReachBottom(() => {
  if (repairStore.hasMore && !loading.value) {
    loadMore()
  }
})

onMounted(() => {
  if (repairStore.list.length === 0) {
    loadList(true)
  }
})
</script>

<style lang="scss" scoped>
.repair-list-page {
  min-height: 100vh;
  background: #f5f5f5;

  &__filter {
    display: flex;
    background: #ffffff;
    padding: 16rpx 24rpx;
    gap: 16rpx;
    overflow-x: auto;
    white-space: nowrap;
  }

  &__content {
    padding: 24rpx;
  }

  &__fab {
    position: fixed;
    right: 32rpx;
    bottom: 120rpx;
    width: 112rpx;
    height: 112rpx;
    background: #1890ff;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    box-shadow: 0 8rpx 24rpx rgba(24, 144, 255, 0.4);
    z-index: 100;
  }

  &__fab-text {
    font-size: 56rpx;
    color: #ffffff;
    line-height: 1;
  }
}

.filter-item {
  flex-shrink: 0;
  padding: 12rpx 32rpx;
  border-radius: 32rpx;
  background: #f5f5f5;
  transition: all 0.3s;

  &--active {
    background: #1890ff;
  }

  &__text {
    font-size: 28rpx;
    color: #666666;
  }

  &--active &__text {
    color: #ffffff;
  }
}

.repair-list {
  padding-bottom: 24rpx;
}

.load-more {
  text-align: center;
  padding: 24rpx;
  font-size: 26rpx;
  color: #999999;
}
</style>
