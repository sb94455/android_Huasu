// 常量定义

// 设备状态
export const EQUIPMENT_STATUS = {
  ACTIVE: 'active',
  BROKEN: 'broken',
  MAINTENANCE: 'maintenance'
} as const

export const EQUIPMENT_STATUS_TEXT: Record<keyof typeof EQUIPMENT_STATUS, string> = {
  ACTIVE: '正常',
  BROKEN: '故障',
  MAINTENANCE: '维修中'
}

export const EQUIPMENT_STATUS_COLOR: Record<keyof typeof EQUIPMENT_STATUS, string> = {
  ACTIVE: '#52c41a',
  BROKEN: '#ff4d4f',
  MAINTENANCE: '#faad14'
}

// 点检任务状态
export const INSPECTION_STATE = {
  DRAFT: 'draft',
  PENDING: 'pending',
  IN_PROGRESS: 'in_progress',
  DONE: 'done'
} as const

export const INSPECTION_STATE_TEXT: Record<keyof typeof INSPECTION_STATE, string> = {
  DRAFT: '草稿',
  PENDING: '待处理',
  IN_PROGRESS: '进行中',
  DONE: '已完成'
}

export const INSPECTION_STATE_COLOR: Record<keyof typeof INSPECTION_STATE, string> = {
  DRAFT: '#d9d9d9',
  PENDING: '#faad14',
  IN_PROGRESS: '#1890ff',
  DONE: '#52c41a'
}

// 报修单状态
export const REPAIR_STATE = {
  DRAFT: 'draft',
  SUBMITTED: 'submitted',
  CONFIRMED: 'confirmed',
  IN_PROGRESS: 'in_progress',
  DONE: 'done',
  CANCELLED: 'cancelled'
} as const

export const REPAIR_STATE_TEXT: Record<keyof typeof REPAIR_STATE, string> = {
  DRAFT: '草稿',
  SUBMITTED: '已提交',
  CONFIRMED: '已确认',
  IN_PROGRESS: '处理中',
  DONE: '已完成',
  CANCELLED: '已取消'
}

export const REPAIR_STATE_COLOR: Record<keyof typeof REPAIR_STATE, string> = {
  DRAFT: '#d9d9d9',
  SUBMITTED: '#faad14',
  CONFIRMED: '#1890ff',
  IN_PROGRESS: '#722ed1',
  DONE: '#52c41a',
  CANCELLED: '#ff4d4f'
}

// 报修优先级
export const REPAIR_PRIORITY = {
  LOW: 'low',
  MEDIUM: 'medium',
  HIGH: 'high',
  URGENT: 'urgent'
} as const

export const REPAIR_PRIORITY_TEXT: Record<keyof typeof REPAIR_PRIORITY, string> = {
  LOW: '低',
  MEDIUM: '中',
  HIGH: '高',
  URGENT: '紧急'
}

export const REPAIR_PRIORITY_COLOR: Record<keyof typeof REPAIR_PRIORITY, string> = {
  LOW: '#52c41a',
  MEDIUM: '#faad14',
  HIGH: '#ff4d4f',
  URGENT: '#f5222d'
}

// 故障类型
export const FAULT_TYPES = [
  '机械故障',
  '电气故障',
  '液压故障',
  '气动故障',
  '软件故障',
  '其他故障'
]

// 日期格式
export const DATE_FORMAT = 'YYYY-MM-DD'
export const DATETIME_FORMAT = 'YYYY-MM-DD HH:mm:ss'

// 分页配置
export const PAGE_SIZE_OPTIONS = [10, 20, 50, 100]
export const DEFAULT_PAGE_SIZE = 20

// 文件上传配置
export const UPLOAD_CONFIG = {
  maxSize: 10 * 1024 * 1024, // 10MB
  acceptTypes: ['image/jpeg', 'image/png', 'image/jpg'],
  maxCount: 9
}

// 缓存键前缀
export const CACHE_KEY_PREFIX = 'huasu_equipment_'

// 本地存储键
export const STORAGE_KEYS = {
  AUTH_INFO: 'auth_info',
  SERVER_CONFIG: 'server_config',
  USER_PREFERENCES: 'user_preferences',
  CACHE_DATA: 'cache_data'
} as const
