import { request } from './request'
import type { Equipment, SearchParams } from './types'

// 设备 API (使用 XML-RPC)
export const equipmentApi = {
  // 获取设备列表
  async getList(params?: SearchParams): Promise<Equipment[]> {
    const authInfo = request.getAuthInfo()
    if (!authInfo) throw new Error('未登录')

    return request.post<Equipment[]>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'object',
        method: 'execute_kw',
        args: [
          authInfo.db,
          authInfo.uid,
          authInfo.password,
          'huasu.equipment',
          'search_read',
          [
            params?.domain || []
          ],
          {
            fields: params?.fields || [
              'id',
              'name',
              'code',
              'category_id',
              'location_id',
              'state',
              'model',
              'manufacturer',
              'purchase_date',
              'warranty_expiry',
              'notes',
              'image_1920'
            ],
            limit: params?.limit || 80,
            offset: params?.offset || ((params?.page || 1) - 1) * (params?.limit || 80),
            order: 'name ASC'
          }
        ]
      },
      id: Date.now()
    })
  },

  // 搜索设备
  async search(keyword: string, page = 1, limit = 20): Promise<Equipment[]> {
    const authInfo = request.getAuthInfo()
    if (!authInfo) throw new Error('未登录')

    return request.post<Equipment[]>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'object',
        method: 'execute_kw',
        args: [
          authInfo.db,
          authInfo.uid,
          authInfo.password,
          'huasu.equipment',
          'search_read',
          [
            ['|', ['name', 'ilike', keyword], ['code', 'ilike', keyword]]
          ],
          {
            fields: ['id', 'name', 'code', 'category_id', 'location_id', 'state'],
            limit,
            offset: (page - 1) * limit,
            order: 'name ASC'
          }
        ]
      },
      id: Date.now()
    })
  },

  // 获取设备详情
  async getDetail(id: number): Promise<Equipment> {
    const authInfo = request.getAuthInfo()
    if (!authInfo) throw new Error('未登录')

    const result = await request.post<Equipment[]>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'object',
        method: 'execute_kw',
        args: [
          authInfo.db,
          authInfo.uid,
          authInfo.password,
          'huasu.equipment',
          'read',
          [[id]],
          {
            fields: [
              'id',
              'name',
              'code',
              'category_id',
              'location_id',
              'state',
              'model',
              'manufacturer',
              'purchase_date',
              'warranty_expiry',
              'notes',
              'image_1920'
            ]
          }
        ]
      },
      id: Date.now()
    })
    return result[0]
  },

  // 获取设备维修历史
  async getRepairHistory(equipmentId: number, limit = 10): Promise<any[]> {
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
          'huasu.repair.order',
          'search_read',
          [
            [['equipment_id', '=', equipmentId]]
          ],
          {
            fields: ['id', 'name', 'date', 'state', 'priority', 'fault_type', 'technician_id'],
            limit,
            offset: 0,
            order: 'date DESC'
          }
        ]
      },
      id: Date.now()
    })
  },

  // 获取设备点检历史
  async getInspectionHistory(equipmentId: number, limit = 10): Promise<any[]> {
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
          'huasu.inspection.task',
          'search_read',
          [
            [['equipment_id', '=', equipmentId]]
          ],
          {
            fields: ['id', 'name', 'date', 'state', 'inspector_id'],
            limit,
            offset: 0,
            order: 'date DESC'
          }
        ]
      },
      id: Date.now()
    })
  }
}
