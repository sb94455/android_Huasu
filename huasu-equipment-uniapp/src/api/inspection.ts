import { request } from './request'
import type { InspectionTask, InspectionResult, SearchParams } from './types'

// 点检 API (使用 XML-RPC)
export const inspectionApi = {
  // 获取点检任务列表
  async getList(params?: SearchParams): Promise<{ data: InspectionTask[]; total: number }> {
    const authInfo = request.getAuthInfo()
    if (!authInfo) throw new Error('未登录')

    const domain = params?.domain || []
    const limit = params?.limit || 80
    const offset = params?.offset || ((params?.page || 1) - 1) * limit

    // 直接用 search_read 获取数据
    const data = await request.post<InspectionTask[]>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'object',
        method: 'execute_kw',
        args: [
          authInfo.db,
          authInfo.uid,
          authInfo.password,
          'inspection.task',
          'search_read',
          domain,
          {
            fields: params?.fields || [
              'id',
              'name',
              'create_date',
              'write_date',
              'state',
              'create_uid',
              'write_uid'
            ],
            limit,
            offset,
            order: 'create_date DESC'
          }
        ]
      },
      id: Date.now()
    })

    const total = data.length >= limit ? -1 : data.length

    return { data, total }
  },

  // 获取待办任务
  async getPendingTasks(page = 1, limit = 20): Promise<InspectionTask[]> {
    const authInfo = request.getAuthInfo()
    if (!authInfo) throw new Error('未登录')

    const domain = [['state', 'in', ['pending', 'in_progress']]]

    return request.post<InspectionTask[]>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'object',
        method: 'execute_kw',
        args: [
          authInfo.db,
          authInfo.uid,
          authInfo.password,
          'inspection.task',
          'search_read',
          domain,
          {
            fields: [
              'id',
              'name',
              'create_date',
              'write_date',
              'state',
              'create_uid',
              'write_uid'
            ],
            limit,
            offset: (page - 1) * limit,
            order: 'create_date ASC'
          }
        ]
      },
      id: Date.now()
    })
  },

  // 获取任务详情
  async getDetail(id: number): Promise<InspectionTask> {
    const authInfo = request.getAuthInfo()
    if (!authInfo) throw new Error('未登录')

    const result = await request.post<InspectionTask[]>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'object',
        method: 'execute_kw',
        args: [
          authInfo.db,
          authInfo.uid,
          authInfo.password,
          'inspection.task',
          'read',
          [[id]],
          {
            fields: [
              'id',
              'name',
              'create_date',
              'write_date',
              'state',
              'create_uid',
              'write_uid'
            ]
          }
        ]
      },
      id: Date.now()
    })
    return result[0]
  },

  // 获取点检表单项
  async getChecklistItems(taskId: number): Promise<any[]> {
    const authInfo = request.getAuthInfo()
    if (!authInfo) throw new Error('未登录')

    return request.post<any[]>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'object',
        method: 'execute_kw',
        args: [
          authInfo.db,
          authInfo.uid,
          authInfo.password,
          'inspection.task',
          'get_checklist_items',
          [taskId],
          {}
        ]
      },
      id: Date.now()
    })
  },

  // 提交点检结果
  async submitResult(taskId: number, result: InspectionResult): Promise<void> {
    const authInfo = request.getAuthInfo()
    if (!authInfo) throw new Error('未登录')

    return request.post<void>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'object',
        method: 'execute_kw',
        args: [
          authInfo.db,
          authInfo.uid,
          authInfo.password,
          'inspection.task',
          'submit_result',
          [taskId, result],
          {}
        ]
      },
      id: Date.now()
    })
  },

  // 开始任务
  async startTask(taskId: number): Promise<void> {
    const authInfo = request.getAuthInfo()
    if (!authInfo) throw new Error('未登录')

    return request.post<void>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'object',
        method: 'execute_kw',
        args: [
          authInfo.db,
          authInfo.uid,
          authInfo.password,
          'inspection.task',
          'action_start',
          [[taskId]],
          {}
        ]
      },
      id: Date.now()
    })
  },

  // 完成任务
  async completeTask(taskId: number): Promise<void> {
    const authInfo = request.getAuthInfo()
    if (!authInfo) throw new Error('未登录')

    return request.post<void>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'object',
        method: 'execute_kw',
        args: [
          authInfo.db,
          authInfo.uid,
          authInfo.password,
          'inspection.task',
          'action_done',
          [[taskId]],
          {}
        ]
      },
      id: Date.now()
    })
  }
}
