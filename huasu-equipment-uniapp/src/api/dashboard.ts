import { request } from './request'

// 仪表盘 API (使用 XML-RPC)
export const dashboardApi = {
  // 获取统计数据
  async getStats(): Promise<any> {
    const authInfo = request.getAuthInfo()
    if (!authInfo) throw new Error('未登录')

    // 使用 search 方法获取 ID 列表来计算总数
    const equipmentIds = await request.post<number[]>('/jsonrpc', {
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
          'search',
          [[]]
        ]
      },
      id: Date.now()
    })

    // 获取各状态的设备数量
    const normalEquipmentIds = await request.post<number[]>('/jsonrpc', {
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
          'search',
          [[['state', '=', 'normal']]]
        ]
      },
      id: Date.now()
    })

    const bugEquipmentIds = await request.post<number[]>('/jsonrpc', {
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
          'search',
          [[['state', '=', 'bug']]]
        ]
      },
      id: Date.now()
    })

    const maintenanceEquipmentIds = await request.post<number[]>('/jsonrpc', {
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
          'search',
          [[['state', '=', 'maintenance']]]
        ]
      },
      id: Date.now()
    })

    const inspectionIds = await request.post<number[]>('/jsonrpc', {
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
          'search',
          [[]]
        ]
      },
      id: Date.now()
    })

    const repairIds = await request.post<number[]>('/jsonrpc', {
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
          'search',
          [[]]
        ]
      },
      id: Date.now()
    })

    // 获取待处理的任务数量 - inspection.task 状态: draft, in_progress, completed
    const pendingInspectionIds = await request.post<number[]>('/jsonrpc', {
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
          'search',
          [[['state', 'in', ['draft', 'in_progress']]]]
        ]
      },
      id: Date.now()
    })

    // 获取待处理的维修工单 - maintenance.work.order 状态: draft, assigned, in_progress01, in_progress02, waiting_approval, done
    const pendingRepairIds = await request.post<number[]>('/jsonrpc', {
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
          'search',
          [[['state', 'in', ['draft', 'assigned', 'in_progress01', 'in_progress02', 'waiting_approval']]]]
        ]
      },
      id: Date.now()
    })

    return {
      equipment: {
        total: equipmentIds?.length || 0,
        active: normalEquipmentIds?.length || 0,
        broken: bugEquipmentIds?.length || 0,
        maintenance: maintenanceEquipmentIds?.length || 0
      },
      inspection: {
        total: inspectionIds?.length || 0,
        pending: pendingInspectionIds?.length || 0,
        completed: 0,
        today: 0
      },
      repair: {
        total: repairIds?.length || 0,
        pending: pendingRepairIds?.length || 0,
        in_progress: 0,
        completed: 0
      },
      pendingTasks: {
        inspections: [],
        repairs: []
      }
    }
  }
}
