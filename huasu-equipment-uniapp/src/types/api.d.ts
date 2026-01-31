// API 响应类型定义
export interface ApiResponse<T = any> {
  jsonrpc?: string
  id?: number | string
  result?: T
  error?: {
    code: number
    message: string
    data?: any
  }
}

// 认证相关类型
export interface LoginRequest {
  db: string
  login: string
  password: string
}

export interface LoginResponse {
  uid: number
  session_id: string
  username: string
  name: string
  company_id?: number
}

// 服务器配置类型
export interface ServerConfig {
  url: string
  db: string
}

// 分页请求类型
export interface PageParams {
  page: number
  limit: number
}

// 搜索参数类型
export interface SearchParams extends PageParams {
  search?: string
  domain?: string[][]
  fields?: string[]
}

// 通用列表响应类型
export interface ListResponse<T> {
  data: T[]
  total: number
  page: number
  limit: number
}
