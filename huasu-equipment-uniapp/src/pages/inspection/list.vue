<template>
  <view class="inspection-list-page">
    <!-- 筛选标签 -->
    <view class="inspection-list-page__filter">
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

    <!-- 任务列表 -->
    <view class="inspection-list-page__content">
      <view v-if="loading" class="loading">
        <text>加载中...</text>
      </view>

      <view v-else-if="filteredList.length === 0" class="empty">
        <text class="empty__icon">📋</text>
        <text class="empty__text">暂无任务</text>
      </view>

      <view v-else class="inspection-list">
        <task-card
          v-for="task in filteredList"
          :key="task.id"
          :task="task"
          type="inspection"
          @click="viewDetail(task.id)"
        />
      </view>

      <!-- 加载更多 -->
      <view v-if="inspectionStore.hasMore && !loading" class="load-more" @click="loadMore">
        <text>加载更多</text>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useInspectionStore } from '@/stores/inspection'

const inspectionStore = useInspectionStore()

const filterOptions = [
  { label: '全部', value: 'all' },
  { label: '待处理', value: 'pending' },
  { label: '进行中', value: 'in_progress' },
  { label: '已完成', value: 'done' }
]

const selectedFilter = ref('all')
const loading = ref(false)

const filteredList = computed(() => {
  const list = inspectionStore.list || []
  if (selectedFilter.value === 'all') {
    return list
  }
  return list.filter(task => task.state === selectedFilter.value)
})

// 加载任务列表
async function loadList(refresh = false) {
  if (loading.value) return

  loading.value = true

  try {
    const page = refresh ? 1 : (inspectionStore.currentPage || 1) + 1
    await inspectionStore.fetchList({ page, limit: 20 }, refresh)
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
  inspectionStore.setFilter(value)
}

// 加载更多
async function loadMore() {
  await loadList(false)
}

// 查看详情
function viewDetail(id: number) {
  uni.navigateTo({ url: `/pages/inspection/detail?id=${id}` })
}

// 下拉刷新
// #ifndef H5
onPullDownRefresh(() => {
  inspectionStore.clearList()
  loadList(true).finally(() => {
    uni.stopPullDownRefresh()
  })
})

// 上拉加载
onReachBottom(() => {
  if (inspectionStore.hasMore && !loading.value) {
    loadMore()
  }
})
// #endif

onMounted(() => {
  if (!inspectionStore.list || inspectionStore.list.length === 0) {
    loadList(true)
  }
})
</script>

<style lang="scss" scoped>
.inspection-list-page {
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

.inspection-list {
  padding-bottom: 24rpx;
}

.load-more {
  text-align: center;
  padding: 24rpx;
  font-size: 26rpx;
  color: #999999;
}
</style>
