import { defineStore } from 'pinia'
import { inspectionApi } from '@/api/inspection'
import type { InspectionTask, InspectionResult, SearchParams } from '@/types'
import { INSPECTION_STATE } from '@/utils/constants'

interface InspectionState {
  list: InspectionTask[]
  current: InspectionTask | null
  pendingList: InspectionTask[]
  total: number
  pendingTotal: number
  loading: boolean
  currentPage: number
  pendingPage: number
  hasMore: boolean
  pendingHasMore: boolean
  selectedFilter: string
}

export const useInspectionStore = defineStore('inspection', {
  state: (): InspectionState => ({
    list: [],
    current: null,
    pendingList: [],
    total: 0,
    pendingTotal: 0,
    loading: false,
    currentPage: 1,
    pendingPage: 1,
    hasMore: true,
    pendingHasMore: true,
    selectedFilter: 'all'
  }),

  getters: {
    // 按状态筛选
    filteredList: (state) => {
      if (state.selectedFilter === 'all') {
        return state.list
      }
      return state.list.filter(task => task.state === state.selectedFilter)
    },

    // 待处理任务数
    pendingCount: (state) => {
      return state.list.filter(t => t.state === INSPECTION_STATE.PENDING).length
    },

    // 进行中任务数
    inProgressCount: (state) => {
      return state.list.filter(t => t.state === INSPECTION_STATE.IN_PROGRESS).length
    }
  },

  actions: {
    // 获取任务列表
    async fetchList(params?: SearchParams, refresh = false) {
      if (this.loading) return

      this.loading = true
      try {
        const page = params?.page || this.currentPage
        const result = await inspectionApi.getList(params)

        if (refresh) {
          this.list = result.data
        } else {
          this.list = [...this.list, ...result.data]
        }

        this.total = result.total
        this.currentPage = page
        this.hasMore = this.list.length < result.total
      } catch (error: any) {
        throw new Error(error.message || '获取点检任务失败')
      } finally {
        this.loading = false
      }
    },

    // 获取待办任务
    async fetchPendingTasks(refresh = false) {
      if (this.loading) return

      this.loading = true
      try {
        const result = await inspectionApi.getPendingTasks(this.pendingPage, 20)

        if (refresh) {
          this.pendingList = result
        } else {
          this.pendingList = [...this.pendingList, ...result]
        }

        this.pendingTotal = result.length
        this.pendingHasMore = result.length >= 20
      } catch (error: any) {
        throw new Error(error.message || '获取待办任务失败')
      } finally {
        this.loading = false
      }
    },

    // 获取任务详情
    async fetchDetail(id: number) {
      try {
        this.current = await inspectionApi.getDetail(id)
        return this.current
      } catch (error: any) {
        throw new Error(error.message || '获取任务详情失败')
      }
    },

    // 开始任务
    async startTask(taskId: number) {
      try {
        await inspectionApi.startTask(taskId)
        // 更新本地状态
        const task = this.list.find(t => t.id === taskId)
        if (task) {
          task.state = INSPECTION_STATE.IN_PROGRESS
        }
      } catch (error: any) {
        throw new Error(error.message || '开始任务失败')
      }
    },

    // 提交结果
    async submitResult(taskId: number, result: InspectionResult) {
      try {
        await inspectionApi.submitResult(taskId, result)
        // 更新本地状态
        const task = this.list.find(t => t.id === taskId)
        if (task) {
          task.state = INSPECTION_STATE.DONE
        }
      } catch (error: any) {
        throw new Error(error.message || '提交结果失败')
      }
    },

    // 设置筛选条件
    setFilter(filter: string) {
      this.selectedFilter = filter
    },

    // 清空列表
    clearList() {
      this.list = []
      this.currentPage = 1
      this.hasMore = true
    },

    // 清空待办列表
    clearPendingList() {
      this.pendingList = []
      this.pendingPage = 1
      this.pendingHasMore = true
    }
  }
})
