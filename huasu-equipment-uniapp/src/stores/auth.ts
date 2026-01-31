import { defineStore } from 'pinia'
import { authApi } from '@/api/auth'
import type { LoginResponse } from '@/types'

interface AuthState {
  sessionId: string
  user: LoginResponse | null
  isLoggedIn: boolean
}

export const useAuthStore = defineStore('auth', {
  state: (): AuthState => ({
    sessionId: '',
    user: null,
    isLoggedIn: false
  }),

  getters: {
    userName: (state) => state.user?.name || '',
    userId: (state) => state.user?.uid || 0
  },

  actions: {
    // 登录
    async login(db: string, username: string, password: string) {
      try {
        const response = await authApi.login({ db, login: username, password })
        this.user = response
        this.sessionId = response.session_id
        this.isLoggedIn = true
        return response
      } catch (error: any) {
        throw new Error(error.message || '登录失败')
      }
    },

    // 登出
    async logout() {
      try {
        await authApi.logout()
      } catch {
        // 忽略登出错误
      } finally {
        this.user = null
        this.sessionId = ''
        this.isLoggedIn = false
      }
    },

    // 检查登录状态
    async checkSession() {
      try {
        const isValid = await authApi.checkSession()
        if (isValid) {
          const user = await authApi.getCurrentUser()
          if (user) {
            this.user = user
            this.sessionId = user.session_id
            this.isLoggedIn = true
            return true
          }
        }
        this.user = null
        this.sessionId = ''
        this.isLoggedIn = false
        return false
      } catch {
        this.user = null
        this.sessionId = ''
        this.isLoggedIn = false
        return false
      }
    },

    // 初始化
    async init() {
      return await this.checkSession()
    }
  }
})
