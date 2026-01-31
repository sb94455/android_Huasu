// 设备相关类型 (基于 Odoo maintenance.equipment.re 模型)
export interface Equipment {
  id: number
  name: string                      // 设备编号 (必填)
  equipment_name: string            // 设备名称 (必填)
  complete_name?: string            // 完整名称 (计算字段)
  state: EquipmentState             // 设备状态

  // 设备信息
  equipment_type?: EquipmentType    // 类型
  level?: EquipmentLevel            // 等级
  model?: string                    // 规格型号
  factory_code?: string             // 出厂编号
  asset_code?: string               // 资产编号

  // 位置相关
  location_id?: number | Array<[number, string]>  // 设备位置 (Many2one)
  location?: string                 // 位置号
  storage_location?: string         // 存放位置

  // 部门和人员
  department_id?: number | Array<[number, string]>  // 所属部门 (Many2one)
  responsible_id?: number | Array<[number, string]>  // 负责人 (Many2one)
  related_personnel?: number[]      // 关联人员 (Many2many)

  // 供应商相关
  brand_id?: number | Array<[number, string]>  // 品牌 (Many2one)
  supplier_id?: number | Array<[number, string]>  // 供应商 (Many2one)

  // 财务信息
  purchase_date?: string            // 购置日期
  purchase_amount?: number          // 购置金额
  warranty_period?: number          // 保修期(月)
  warranty_end_date?: string        // 保修到期日 (计算)
  warranty_status?: WarrantyStatus  // 保修状态 (计算)

  // 其他
  expected_scrap_date?: string      // 预计报废日期
  qc_num?: string                   // 二维码字段
  notes?: string                    // 备注
  main_image?: string               // 设备首页图
  detail_images?: any[]             // 设备详情 (Many2many)

  // 布尔字段
  is_monitored?: boolean            // 是否监控
  is_controlled?: boolean           // 是否管控

  // 计算字段
  equipment_age?: number            // 设备年龄(年)
  days_to_scrap?: number            // 距报废天数

  // 标准字段
  create_date?: string
  write_date?: string
  create_uid?: number | Array<[number, string]>
  write_uid?: number | Array<[number, string]>
}

// 设备状态枚举
export type EquipmentState = 'normal' | 'bug' | 'maintenance' | 'scrapped'

// 设备类型枚举
export type EquipmentType = 'production' | 'office' | 'testing' | 'other'

// 设备等级枚举
export type EquipmentLevel = 'a' | 'b' | 'c'

// 保修状态枚举
export type WarrantyStatus = 'in_warranty' | 'out_of_warranty' | 'no_warranty'

// 点检任务相关类型
export interface InspectionTask {
  id: number
  name: string
  create_date: string
  write_date?: string
  state: 'draft' | 'pending' | 'in_progress' | 'done'
  create_uid?: number | Array<[number, string]>
  write_uid?: number | Array<[number, string]>
  equipment_id?: number
  equipment_name?: string
  inspector_id?: number
  inspector_name?: string
  route_id?: number
  route_name?: string
  checklist_template_id?: number
  notes?: string
}

export interface ChecklistItem {
  id: number
  name: string
  description?: string
  type: 'boolean' | 'numeric' | 'text' | 'select'
  required: boolean
  options?: string[]
  min_value?: number
  max_value?: number
}

export interface InspectionResult {
  task_id: number
  items: {
    checklist_item_id: number
    value: boolean | number | string
    notes?: string
  }[]
  notes?: string
  images?: string[]
}

// 报修单相关类型
export interface RepairOrder {
  id: number
  name: string
  create_date: string
  write_date?: string
  state: 'draft' | 'submitted' | 'confirmed' | 'in_progress' | 'done' | 'cancelled'
  priority: 'low' | 'medium' | 'high' | 'urgent'
  fault_type?: string
  fault_description: string
  create_uid?: number | Array<[number, string]>
  write_uid?: number | Array<[number, string]>
  equipment_id?: number
  equipment_name?: string
  reporter_id?: number
  reporter_name?: string
  technician_id?: number
  technician_name?: string
  estimated_completion_date?: string
  actual_completion_date?: string
  notes?: string
  images?: string[]
}

export interface CreateRepairRequest {
  equipment_id: number
  fault_type: string
  fault_description: string
  priority: 'low' | 'medium' | 'high' | 'urgent'
  notes?: string
  images?: string[]
}

// 仪表盘统计数据类型
export interface DashboardStats {
  equipment: {
    total: number
    active: number
    broken: number
    maintenance: number
  }
  inspection: {
    total: number
    pending: number
    completed: number
    today: number
  }
  repair: {
    total: number
    pending: number
    in_progress: number
    completed: number
  }
  pendingTasks: {
    inspections: InspectionTask[]
    repairs: RepairOrder[]
  }
}
