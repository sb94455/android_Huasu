import { request } from './request'
import type { InspectionTask, InspectionResult, SearchParams } from './types'

// 点检 API
export const inspectionApi = {
  // 获取点检任务列表
  async getList(params?: SearchParams): Promise<{ data: InspectionTask[]; total: number }> {
    return request.post('/web/dataset/search_read', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        model: 'huasu.inspection.task',
        domain: params?.domain || [],
        fields: params?.fields || [
          'id',
          'name',
          'equipment_id',
          'date',
          'state',
          'inspector_id',
          'route_id',
          'checklist_template_id',
          'notes'
        ],
        limit: params?.limit || 80,
        offset: params?.offset || ((params?.page || 1) - 1) * (params?.limit || 80),
        order: 'date DESC'
      },
      id: Date.now()
    })
  },

  // 获取待办任务
  async getPendingTasks(page = 1, limit = 20): Promise<{ data: InspectionTask[]; total: number }> {
    return request.post('/web/dataset/search_read', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        model: 'huasu.inspection.task',
        domain: [['state', 'in', ['pending', 'in_progress']]],
        fields: [
          'id',
          'name',
          'equipment_id',
          'date',
          'state',
          'inspector_id',
          'route_id'
        ],
        limit,
        offset: (page - 1) * limit,
        order: 'date ASC'
      },
      id: Date.now()
    })
  },

  // 获取任务详情
  async getDetail(id: number): Promise<InspectionTask> {
    return request.post('/web/dataset/call_kw', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        model: 'huasu.inspection.task',
        method: 'read',
        args: [[id]],
        kwargs: {
          fields: [
            'id',
            'name',
            'equipment_id',
            'date',
            'state',
            'inspector_id',
            'route_id',
            'checklist_template_id',
            'notes'
          ]
        }
      },
      id: Date.now()
    }).then((result: any[]) => result[0])
  },

  // 获取点检表单项
  async getChecklistItems(taskId: number): Promise<any[]> {
    return request.post('/web/dataset/call_kw', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        model: 'huasu.inspection.task',
        method: 'get_checklist_items',
        args: [taskId],
        kwargs: {}
      },
      id: Date.now()
    })
  },

  // 提交点检结果
  async submitResult(taskId: number, result: InspectionResult): Promise<void> {
    return request.post('/web/dataset/call_kw', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        model: 'huasu.inspection.task',
        method: 'submit_result',
        args: [taskId, result],
        kwargs: {}
      },
      id: Date.now()
    })
  },

  // 开始任务
  async startTask(taskId: number): Promise<void> {
    return request.post('/web/dataset/call_kw', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        model: 'huasu.inspection.task',
        method: 'action_start',
        args: [[taskId]],
        kwargs: {}
      },
      id: Date.now()
    })
  },

  // 完成任务
  async completeTask(taskId: number): Promise<void> {
    return request.post('/web/dataset/call_kw', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        model: 'huasu.inspection.task',
        method: 'action_done',
        args: [[taskId]],
        kwargs: {}
      },
      id: Date.now()
    })
  }
}
