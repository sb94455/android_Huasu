import { request } from './request'
import type { LoginRequest, LoginResponse, ServerConfig } from './types'
import { storage } from '@/utils/storage'

const AUTH_KEY = 'auth_info'
const SERVER_KEY = 'server_config'

// 认证 API
export const authApi = {
  // 登录 (使用 XML-RPC common.login)
  async login(data: LoginRequest): Promise<LoginResponse> {
    // 第一步：调用 common.login 获取 uid
    const uid = await request.post<number>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'common',
        method: 'login',
        args: [data.db, data.login, data.password]
      },
      id: Date.now()
    })

    if (!uid || uid <= 0) {
      throw new Error('登录失败，用户名或密码错误')
    }

    // 第二步：获取用户信息
    const userInfo = await request.post<any[]>('/jsonrpc', {
      jsonrpc: '2.0',
      method: 'call',
      params: {
        service: 'object',
        method: 'execute_kw',
        args: [
          data.db,
          uid,
          data.password,
          'res.users',
          'read',
          [[uid]],
          { fields: ['id', 'name', 'login', 'email'] }
        ]
      },
      id: Date.now()
    })

    const user = userInfo[0]

    // 构造响应
    const response: LoginResponse = {
      uid: uid,
      session_id: '', // XML-RPC 不需要 session_id
      username: user.login,
      name: user.name
    }

    // 保存认证信息（保存密码用于后续请求）
    await storage.set(AUTH_KEY, {
      sessionId: '',
      uid: uid,
      username: user.login,
      name: user.name,
      password: data.password, // 保存密码用于后续 XML-RPC 请求
      db: data.db
    })

    // 更新 request 的认证信息
    request.setSessionId('')
    ;(request as any).setAuthInfo({ uid, password: data.password, db: data.db })

    return response
  },

  // 登出
  async logout(): Promise<void> {
    try {
      // XML-RPC 不需要显式登出请求
    } finally {
      request.clearSession()
      await storage.remove(AUTH_KEY)
    }
  },

  // 检查登录状态
  async checkSession(): Promise<boolean> {
    try {
      const authInfo = await storage.get(AUTH_KEY)
      if (!authInfo || !authInfo.uid || !authInfo.password) {
        return false
      }

      // 恢复认证信息到 request
      request.setAuthInfo({
        uid: authInfo.uid,
        password: authInfo.password,
        db: authInfo.db
      })

      // 尝试调用一个简单的 API 验证
      await request.post('/jsonrpc', {
        jsonrpc: '2.0',
        method: 'call',
        params: {
          service: 'common',
          method: 'version',
          args: []
        },
        id: Date.now()
      })

      return true
    } catch {
      return false
    }
  },

  // 获取当前用户信息
  async getCurrentUser(): Promise<LoginResponse | null> {
    try {
      const authInfo = await storage.get(AUTH_KEY)
      if (!authInfo || !authInfo.uid) {
        return null
      }

      // 恢复认证信息到 request
      request.setAuthInfo({
        uid: authInfo.uid,
        password: authInfo.password,
        db: authInfo.db
      })

      return {
        uid: authInfo.uid,
        session_id: '',
        username: authInfo.username,
        name: authInfo.name
      }
    } catch {
      return null
    }
  },

  // 保存服务器配置
  async saveServerConfig(config: ServerConfig): Promise<void> {
    await storage.set(SERVER_KEY, config)
    request.setBaseURL(config.url)
  },

  // 获取服务器配置
  async getServerConfig(): Promise<ServerConfig | null> {
    try {
      const config = await storage.get(SERVER_KEY)
      if (config) {
        request.setBaseURL(config.url)
      }
      return config
    } catch {
      return null
    }
  },

  // 清除所有数据
  async clearAll(): Promise<void> {
    await storage.remove(AUTH_KEY)
    await storage.remove(SERVER_KEY)
    request.clearSession()
  }
}
