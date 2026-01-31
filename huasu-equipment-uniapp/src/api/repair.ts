import { request } from './request'
import type { RepairOrder, CreateRepairRequest, SearchParams } from './types'

// 报修 API (使用 XML-RPC)
export const repairApi = {
  // 获取报修单列表
  async getList(params?: SearchParams): Promise<{ data: RepairOrder[]; total: number }> {
    const authInfo = request.getAuthInfo()
    if (!authInfo) throw new Error('未登录')

    const domain = params?.domain || []
    const limit = params?.limit || 80
    const offset = params?.offset || ((params?.page || 1) - 1) * limit

    // 直接用 search_read 获取数据
    const data = await request.post<RepairOrder[]>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'object',
        method: 'execute_kw',
        args: [
          authInfo.db,
          authInfo.uid,
          authInfo.password,
          'maintenance.work.order',
          'search_read',
          domain,
          {
            fields: params?.fields || [
              'id',
              'name',
              'create_date',
              'write_date',
              'state',
              'priority',
              'fault_type',
              'fault_description',
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

  // 获取我的报修单
  async getMyRepairs(page = 1, limit = 20): Promise<RepairOrder[]> {
    const authInfo = request.getAuthInfo()
    if (!authInfo) throw new Error('未登录')

    const domain = [['create_uid', '=', authInfo.uid]]

    return request.post<RepairOrder[]>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'object',
        method: 'execute_kw',
        args: [
          authInfo.db,
          authInfo.uid,
          authInfo.password,
          'maintenance.work.order',
          'search_read',
          domain,
          {
            fields: [
              'id',
              'name',
              'create_date',
              'write_date',
              'state',
              'priority',
              'fault_type',
              'fault_description',
              'create_uid',
              'write_uid'
            ],
            limit,
            offset: (page - 1) * limit,
            order: 'create_date DESC'
          }
        ]
      },
      id: Date.now()
    })
  },

  // 获取报修单详情
  async getDetail(id: number): Promise<RepairOrder> {
    const authInfo = request.getAuthInfo()
    if (!authInfo) throw new Error('未登录')

    const result = await request.post<RepairOrder[]>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'object',
        method: 'execute_kw',
        args: [
          authInfo.db,
          authInfo.uid,
          authInfo.password,
          'maintenance.work.order',
          'read',
          [[id]],
          {
            fields: [
              'id',
              'name',
              'create_date',
              'write_date',
              'state',
              'priority',
              'fault_type',
              'fault_description',
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

  // 创建报修单
  async create(data: CreateRepairRequest): Promise<number> {
    const authInfo = request.getAuthInfo()
    if (!authInfo) throw new Error('未登录')

    return request.post<number>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'object',
        method: 'execute_kw',
        args: [
          authInfo.db,
          authInfo.uid,
          authInfo.password,
          'maintenance.work.order',
          'create',
          [data],
          {}
        ]
      },
      id: Date.now()
    })
  },

  // 提交报修单
  async submit(id: number): Promise<void> {
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
          'maintenance.work.order',
          'action_submit',
          [[id]],
          {}
        ]
      },
      id: Date.now()
    })
  },

  // 取消报修单
  async cancel(id: number): Promise<void> {
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
          'maintenance.work.order',
          'action_cancel',
          [[id]],
          {}
        ]
      },
      id: Date.now()
    })
  }
}
