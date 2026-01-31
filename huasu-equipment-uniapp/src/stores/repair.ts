import { defineStore } from 'pinia'
import { repairApi } from '@/api/repair'
import type { RepairOrder, CreateRepairRequest, SearchParams } from '@/types'
import { REPAIR_STATE } from '@/utils/constants'

interface RepairState {
  list: RepairOrder[]
  current: RepairOrder | null
  total: number
  loading: boolean
  currentPage: number
  hasMore: boolean
  selectedFilter: string
}

export const useRepairStore = defineStore('repair', {
  state: (): RepairState => ({
    list: [],
    current: null,
    total: 0,
    loading: false,
    currentPage: 1,
    hasMore: true,
    selectedFilter: 'all'
  }),

  getters: {
    // 按状态筛选
    filteredList: (state) => {
      if (state.selectedFilter === 'all') {
        return state.list
      }
      return state.list.filter(order => order.state === state.selectedFilter)
    },

    // 待处理报修单数
    pendingCount: (state) => {
      return state.list.filter(r =>
        r.state === REPAIR_STATE.SUBMITTED || r.state === REPAIR_STATE.CONFIRMED
      ).length
    },

    // 处理中报修单数
    inProgressCount: (state) => {
      return state.list.filter(r => r.state === REPAIR_STATE.IN_PROGRESS).length
    }
  },

  actions: {
    // 获取报修单列表
    async fetchList(params?: SearchParams, refresh = false) {
      if (this.loading) return

      this.loading = true
      try {
        const page = params?.page || this.currentPage
        const result = await repairApi.getList(params)

        if (refresh) {
          this.list = result.data
        } else {
          this.list = [...this.list, ...result.data]
        }

        this.total = result.total
        this.currentPage = page
        this.hasMore = this.list.length < result.total
      } catch (error: any) {
        throw new Error(error.message || '获取报修单失败')
      } finally {
        this.loading = false
      }
    },

    // 获取我的报修单
    async fetchMyRepairs(refresh = false) {
      if (this.loading) return

      this.loading = true
      try {
        const result = await repairApi.getMyRepairs(this.currentPage, 20)

        if (refresh) {
          this.list = result.data
        } else {
          this.list = [...this.list, ...result.data]
        }

        this.total = result.total
        this.hasMore = this.list.length < result.total
      } catch (error: any) {
        throw new Error(error.message || '获取我的报修单失败')
      } finally {
        this.loading = false
      }
    },

    // 获取报修单详情
    async fetchDetail(id: number) {
      try {
        this.current = await repairApi.getDetail(id)
        return this.current
      } catch (error: any) {
        throw new Error(error.message || '获取报修单详情失败')
      }
    },

    // 创建报修单
    async createRepair(data: CreateRepairRequest) {
      try {
        const id = await repairApi.create(data)
        return id
      } catch (error: any) {
        throw new Error(error.message || '创建报修单失败')
      }
    },

    // 提交报修单
    async submitRepair(id: number) {
      try {
        await repairApi.submit(id)
        // 更新本地状态
        const order = this.list.find(r => r.id === id)
        if (order) {
          order.state = REPAIR_STATE.SUBMITTED
        }
      } catch (error: any) {
        throw new Error(error.message || '提交报修单失败')
      }
    },

    // 取消报修单
    async cancelRepair(id: number) {
      try {
        await repairApi.cancel(id)
        // 更新本地状态
        const order = this.list.find(r => r.id === id)
        if (order) {
          order.state = REPAIR_STATE.CANCELLED
        }
      } catch (error: any) {
        throw new Error(error.message || '取消报修单失败')
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
    }
  }
})
