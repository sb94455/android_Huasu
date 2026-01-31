import { request } from './request'
import type { Equipment, SearchParams } from './types'
import { EQUIPMENT_LIST_FIELDS, EQUIPMENT_DETAIL_FIELDS } from '@/config/fields'

// 设备 API (使用 XML-RPC)
export const equipmentApi = {
  // 获取模型可用字段（用于调试）
  async getFields(): Promise<Record<string, any>> {
    const authInfo = request.getAuthInfo()
    if (!authInfo) throw new Error('未登录')

    const result = await request.post<Record<string, any>>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'object',
        method: 'execute_kw',
        args: [
          authInfo.db,
          authInfo.uid,
          authInfo.password,
          'maintenance.equipment.re',
          'fields_get',
          [],
          {}
        ]
      },
      id: Date.now()
    })
    return result
  },

  // 获取设备列表
  async getList(params?: SearchParams): Promise<{ data: Equipment[]; total: number }> {
    const authInfo = request.getAuthInfo()
    if (!authInfo) throw new Error('未登录')

    const domain = params?.domain || []
    const limit = params?.limit || 10
    const offset = params?.offset || ((params?.page || 1) - 1) * limit

    // 使用配置的字段列表
    const fields = params?.fields || EQUIPMENT_LIST_FIELDS

    // 直接用 search_read 获取数据
    const data = await request.post<Equipment[]>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'object',
        method: 'execute_kw',
        args: [
          authInfo.db,
          authInfo.uid,
          authInfo.password,
          'maintenance.equipment.re',
          'search_read',
          domain,
          {
            fields,
            limit,
            offset,
            order: 'name ASC'
          }
        ]
      },
      id: Date.now()
    })

    const total = data.length >= limit ? -1 : data.length

    return { data, total }
  },

  // 搜索设备
  async search(keyword: string, page = 1, limit = 20): Promise<{ data: Equipment[]; total: number }> {
    const authInfo = request.getAuthInfo()
    if (!authInfo) throw new Error('未登录')

    const domain = [['name', 'ilike', keyword]]

    // 直接用 search_read 获取数据
    const data = await request.post<Equipment[]>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'object',
        method: 'execute_kw',
        args: [
          authInfo.db,
          authInfo.uid,
          authInfo.password,
          'maintenance.equipment.re',
          'search_read',
          domain,
          {
            fields: ['id', 'name', 'state'],
            limit,
            offset: (page - 1) * limit,
            order: 'name ASC'
          }
        ]
      },
      id: Date.now()
    })

    const total = data.length >= limit ? -1 : data.length

    return { data, total }
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
          'maintenance.equipment.re',
          'read',
          [[id]],
          {
            fields: EQUIPMENT_DETAIL_FIELDS
          }
        ]
      },
      id: Date.now()
    })
    return result[0]
  }
}
