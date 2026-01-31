import { defineStore } from 'pinia'
import { equipmentApi } from '@/api/equipment'
import type { Equipment, SearchParams } from '@/types'

interface EquipmentState {
  list: Equipment[]
  current: Equipment | null
  total: number
  loading: boolean
  searchKeyword: string
  currentPage: number
  hasMore: boolean
}

export const useEquipmentStore = defineStore('equipment', {
  state: (): EquipmentState => ({
    list: [],
    current: null,
    total: 0,
    loading: false,
    searchKeyword: '',
    currentPage: 1,
    hasMore: true
  }),

  getters: {
    // 正常设备 (state: normal)
    normalEquipment: (state) => state.list.filter(e => e.state === 'normal'),
    // 异常设备 (state: bug)
    brokenEquipment: (state) => state.list.filter(e => e.state === 'bug'),
    // 维修中设备 (state: maintenance)
    maintenanceEquipment: (state) => state.list.filter(e => e.state === 'maintenance'),
    // 已报废设备 (state: scrapped)
    scrappedEquipment: (state) => state.list.filter(e => e.state === 'scrapped')
  },

  actions: {
    // 获取设备列表
    async fetchList(params?: SearchParams, refresh = false) {
      if (this.loading) return

      this.loading = true
      try {
        const page = params?.page || this.currentPage
        const result = await equipmentApi.getList(params)

        if (refresh) {
          this.list = result.data
        } else {
          this.list = [...this.list, ...result.data]
        }

        this.total = result.total
        this.currentPage = page
        this.hasMore = this.list.length < result.total
      } catch (error: any) {
        throw new Error(error.message || '获取设备列表失败')
      } finally {
        this.loading = false
      }
    },

    // 搜索设备
    async search(keyword: string, page = 1) {
      this.searchKeyword = keyword
      this.currentPage = page
      return await this.fetchList({ page, limit: 20 }, true)
    },

    // 获取设备详情
    async fetchDetail(id: number) {
      try {
        this.current = await equipmentApi.getDetail(id)
        return this.current
      } catch (error: any) {
        throw new Error(error.message || '获取设备详情失败')
      }
    },

    // 清空列表
    clearList() {
      this.list = []
      this.currentPage = 1
      this.hasMore = true
      this.searchKeyword = ''
    },

    // 清空详情
    clearDetail() {
      this.current = null
    }
  }
})
