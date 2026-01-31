import { defineStore } from 'pinia'
import { authApi } from '@/api/auth'
import type { ServerConfig } from '@/types'

interface ServerState {
  config: ServerConfig | null
  isConfigured: boolean
}

const DEFAULT_CONFIG: ServerConfig = {
  url: 'http://192.168.1.100:8069',
  db: 'huasu_production'
}

export const useServerStore = defineStore('server', {
  state: (): ServerState => ({
    config: null,
    isConfigured: false
  }),

  getters: {
    serverUrl: (state) => state.config?.url || '',
    dbName: (state) => state.config?.db || ''
  },

  actions: {
    // 加载配置
    async loadConfig() {
      try {
        const config = await authApi.getServerConfig()
        if (config) {
          this.config = config
          this.isConfigured = true
          return config
        }
        return null
      } catch {
        return null
      }
    },

    // 保存配置
    async saveConfig(config: ServerConfig) {
      try {
        await authApi.saveServerConfig(config)
        this.config = config
        this.isConfigured = true
      } catch (error: any) {
        throw new Error(error.message || '保存配置失败')
      }
    },

    // 获取默认配置
    getDefaultConfig(): ServerConfig {
      return { ...DEFAULT_CONFIG }
    },

    // 清除配置
    async clearConfig() {
      this.config = null
      this.isConfigured = false
    }
  }
})
