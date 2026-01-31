// 导出所有类型定义
export * from '@/types/api'
export * from '@/types/model'

// Odoo JSON-RPC 请求结构
export interface JsonRpcRequest {
  jsonrpc: '2.0'
  method: string
  params: any[]
  id: number | string
}

// Odoo 搜索域
export type OdooDomain = any[]

// Odoo 查询参数
export interface OdooSearchParams {
  domain?: OdooDomain
  fields?: string[]
  limit?: number
  offset?: number
  order?: string
}

// 模型通用操作
export interface ModelOperations {
  search_read: (params: OdooSearchParams) => Promise<any[]>
  read: (ids: number[], fields?: string[]) => Promise<any[]>
  search: (domain?: OdooDomain, limit?: number, offset?: number) => Promise<number[]>
  create: (data: Record<string, any>) => Promise<number>
  write: (ids: number[], data: Record<string, any>) => Promise<boolean>
  unlink: (ids: number[]) => Promise<boolean>
}

// 生成唯一请求ID
let requestId = 0
export const generateRequestId = (): number => {
  return ++requestId
}

// 构建 JSON-RPC 请求
export const buildJsonRpcRequest = (method: string, params: any[], id?: number): JsonRpcRequest => {
  return {
    jsonrpc: '2.0',
    method,
    params,
    id: id || generateRequestId()
  }
}
