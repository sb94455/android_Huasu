<script setup lang="ts">
import { onLaunch, onShow, onHide } from '@dcloudio/uni-app'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()

onLaunch(() => {
  // 初始化应用
  authStore.init()
})

onShow(() => {
  // 应用显示
})

onHide(() => {
  // 应用隐藏
})
</script>

<style lang="scss">
@import '@/styles/index.scss';

page {
  background-color: #f5f5f5;
  font-size: 28rpx;
  line-height: 1.6;
}
</style>
