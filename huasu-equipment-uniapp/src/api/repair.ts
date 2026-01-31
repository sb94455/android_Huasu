import { request } from './request'
import type { RepairOrder, CreateRepairRequest, SearchParams } from './types'

// 报修 API
export const repairApi = {
  // 获取报修单列表
  async getList(params?: SearchParams): Promise<{ data: RepairOrder[]; total: number }> {
    return request.post('/web/dataset/search_read', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        model: 'huasu.repair.order',
        domain: params?.domain || [],
        fields: params?.fields || [
          'id',
          'name',
          'equipment_id',
          'date',
          'state',
          'priority',
          'fault_type',
          'fault_description',
          'reporter_id',
          'technician_id',
          'estimated_completion_date',
          'actual_completion_date'
        ],
        limit: params?.limit || 80,
        offset: params?.offset || ((params?.page || 1) - 1) * (params?.limit || 80),
        order: 'date DESC'
      },
      id: Date.now()
    })
  },

  // 获取我的报修单
  async getMyRepairs(page = 1, limit = 20): Promise<{ data: RepairOrder[]; total: number }> {
    return request.post('/web/dataset/search_read', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        model: 'huasu.repair.order',
        domain: [['create_uid', '=', true]],
        fields: [
          'id',
          'name',
          'equipment_id',
          'date',
          'state',
          'priority',
          'fault_type',
          'fault_description'
        ],
        limit,
        offset: (page - 1) * limit,
        order: 'date DESC'
      },
      id: Date.now()
    })
  },

  // 获取报修单详情
  async getDetail(id: number): Promise<RepairOrder> {
    return request.post('/web/dataset/call_kw', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        model: 'huasu.repair.order',
        method: 'read',
        args: [[id]],
        kwargs: {
          fields: [
            'id',
            'name',
            'equipment_id',
            'date',
            'state',
            'priority',
            'fault_type',
            'fault_description',
            'reporter_id',
            'technician_id',
            'estimated_completion_date',
            'actual_completion_date',
            'notes',
            'image_1920'
          ]
        }
      },
      id: Date.now()
    }).then((result: any[]) => result[0])
  },

  // 创建报修单
  async create(data: CreateRepairRequest): Promise<number> {
    return request.post('/web/dataset/call_kw', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        model: 'huasu.repair.order',
        method: 'create',
        args: [data],
        kwargs: {}
      },
      id: Date.now()
    })
  },

  // 提交报修单
  async submit(id: number): Promise<void> {
    return request.post('/web/dataset/call_kw', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        model: 'huasu.repair.order',
        method: 'action_submit',
        args: [[id]],
        kwargs: {}
      },
      id: Date.now()
    })
  },

  // 取消报修单
  async cancel(id: number): Promise<void> {
    return request.post('/web/dataset/call_kw', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        model: 'huasu.repair.order',
        method: 'action_cancel',
        args: [[id]],
        kwargs: {}
      },
      id: Date.now()
    })
  }
}
