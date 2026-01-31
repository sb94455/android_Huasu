<template>
  <view class="equipment-list-page">
    <!-- 搜索栏 -->
    <view class="equipment-list-page__search">
      <view class="search-bar">
        <text class="search-bar__icon">🔍</text>
        <input
          class="search-bar__input"
          v-model="searchKeyword"
          placeholder="搜索设备名称或编号"
          confirm-type="search"
          @confirm="handleSearch"
        />
        <text v-if="searchKeyword" class="search-bar__clear" @click="clearSearch">✕</text>
      </view>
    </view>

    <!-- 设备列表 -->
    <view class="equipment-list-page__content">
      <view v-if="loading" class="loading">
        <text>加载中...</text>
      </view>

      <view v-else-if="equipmentStore.list.length === 0" class="empty">
        <text class="empty__icon">📦</text>
        <text class="empty__text">暂无设备</text>
      </view>

      <view v-else class="equipment-list">
        <equipment-card
          v-for="equipment in equipmentStore.list"
          :key="equipment.id"
          :equipment="equipment"
          @click="viewDetail(equipment.id)"
          @detail="viewDetail(equipment.id)"
          @repair="createRepair(equipment.id)"
        />
      </view>

      <!-- 加载更多 -->
      <view v-if="equipmentStore.hasMore && !loading" class="load-more" @click="loadMore">
        <text>加载更多</text>
      </view>
    </view>

    <!-- 悬浮按钮 -->
    <view class="equipment-list-page__fab" @click="scanCode">
      <text class="equipment-list-page__fab-text">📷</text>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useEquipmentStore } from '@/stores/equipment'

const equipmentStore = useEquipmentStore()

const searchKeyword = ref('')
const loading = ref(false)

// 加载设备列表
async function loadList(refresh = false) {
  if (loading.value) return

  loading.value = true

  try {
    const page = refresh ? 1 : equipmentStore.currentPage + 1
    await equipmentStore.fetchList({ page, limit: 20 }, refresh)
  } catch (error: any) {
    uni.showToast({
      title: error.message || '加载失败',
      icon: 'none'
    })
  } finally {
    loading.value = false
  }
}

// 搜索设备
async function handleSearch() {
  if (!searchKeyword.value.trim()) {
    equipmentStore.clearList()
    await loadList(true)
    return
  }

  loading.value = true

  try {
    await equipmentStore.search(searchKeyword.value)
  } catch (error: any) {
    uni.showToast({
      title: error.message || '搜索失败',
      icon: 'none'
    })
  } finally {
    loading.value = false
  }
}

// 清除搜索
function clearSearch() {
  searchKeyword.value = ''
  equipmentStore.clearList()
  loadList(true)
}

// 加载更多
async function loadMore() {
  await loadList(false)
}

// 查看详情
function viewDetail(id: number) {
  uni.navigateTo({ url: `/pages/equipment/detail?id=${id}` })
}

// 创建报修
function createRepair(equipmentId: number) {
  uni.navigateTo({ url: `/pages/repair/create?equipmentId=${equipmentId}` })
}

// 扫码
function scanCode() {
  uni.scanCode({
    success: (res) => {
      // 扫码成功，跳转到设备详情
      searchKeyword.value = res.result
      handleSearch()
    },
    fail: () => {
      uni.showToast({
        title: '扫码失败',
        icon: 'none'
      })
    }
  })
}

// 下拉刷新
onPullDownRefresh(() => {
  equipmentStore.clearList()
  loadList(true).finally(() => {
    uni.stopPullDownRefresh()
  })
})

// 上拉加载
onReachBottom(() => {
  if (equipmentStore.hasMore && !loading.value) {
    loadMore()
  }
})

onMounted(() => {
  if (equipmentStore.list.length === 0) {
    loadList(true)
  }
})
</script>

<style lang="scss" scoped>
.equipment-list-page {
  min-height: 100vh;
  background: #f5f5f5;

  &__search {
    position: sticky;
    top: 0;
    z-index: 10;
    background: #ffffff;
    padding: 16rpx 24rpx;
    box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
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
    font-size: 48rpx;
  }
}

.search-bar {
  display: flex;
  align-items: center;
  height: 64rpx;
  background: #f5f5f5;
  border-radius: 32rpx;
  padding: 0 24rpx;

  &__icon {
    font-size: 32rpx;
    margin-right: 16rpx;
  }

  &__input {
    flex: 1;
    height: 100%;
    font-size: 28rpx;
    background: transparent;
  }

  &__clear {
    font-size: 32rpx;
    color: #999999;
    margin-left: 16rpx;
    padding: 8rpx;
  }
}

.equipment-list {
  padding-bottom: 24rpx;
}

.load-more {
  text-align: center;
  padding: 24rpx;
  font-size: 26rpx;
  color: #999999;
}
</style>
