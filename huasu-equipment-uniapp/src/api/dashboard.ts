import { request } from './request'
import type { DashboardStats } from './types'

// 仪表盘 API
export const dashboardApi = {
  // 获取统计数据
  async getStats(): Promise<DashboardStats> {
    return request.post<DashboardStats>('/web/dataset/call_kw', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        model: 'huasu.equipment',
        method: 'get_dashboard_stats',
        args: [],
        kwargs: {}
      },
      id: Date.now()
    })
  },

  // 获取设备统计
  async getEquipmentStats(): Promise<{ total: number; active: number; broken: number; maintenance: number }> {
    return request.post('/web/dataset/call_kw', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        model: 'huasu.equipment',
        method: 'get_equipment_stats',
        args: [],
        kwargs: {}
      },
      id: Date.now()
    })
  },

  // 获取点检统计
  async getInspectionStats(): Promise<{ total: number; pending: number; completed: number; today: number }> {
    return request.post('/web/dataset/call_kw', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        model: 'huasu.inspection.task',
        method: 'get_inspection_stats',
        args: [],
        kwargs: {}
      },
      id: Date.now()
    })
  },

  // 获取报修统计
  async getRepairStats(): Promise<{ total: number; pending: number; in_progress: number; completed: number }> {
    return request.post('/web/dataset/call_kw', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        model: 'huasu.repair.order',
        method: 'get_repair_stats',
        args: [],
        kwargs: {}
      },
      id: Date.now()
    })
  },

  // 获取待办任务
  async getPendingTasks(): Promise<{ inspections: any[]; repairs: any[] }> {
    return request.post('/web/dataset/call_kw', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        model: 'huasu.dashboard',
        method: 'get_pending_tasks',
        args: [],
        kwargs: {}
      },
      id: Date.now()
    })
  }
}
