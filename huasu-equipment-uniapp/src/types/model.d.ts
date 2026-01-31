// 设备相关类型
export interface Equipment {
  id: number
  name: string
  code: string
  category?: string
  location?: string
  status: 'active' | 'broken' | 'maintenance'
  model?: string
  manufacturer?: string
  purchase_date?: string
  warranty_expiry?: string
  notes?: string
  image_url?: string
}

// 点检任务相关类型
export interface InspectionTask {
  id: number
  name: string
  equipment_id: number
  equipment_name?: string
  date: string
  state: 'draft' | 'pending' | 'in_progress' | 'done'
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
  equipment_id: number
  equipment_name?: string
  date: string
  state: 'draft' | 'submitted' | 'confirmed' | 'in_progress' | 'done' | 'cancelled'
  priority: 'low' | 'medium' | 'high' | 'urgent'
  fault_type?: string
  fault_description: string
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
