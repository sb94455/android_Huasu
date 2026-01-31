import type { ApiResponse } from '@/types/api'

// 请求配置接口
interface RequestConfig {
  url: string
  method?: 'GET' | 'POST' | 'PUT' | 'DELETE'
  data?: any
  header?: Record<string, string>
  timeout?: number
}

// 请求拦截器类型
type RequestInterceptor = (config: RequestConfig) => RequestConfig
type ResponseInterceptor = (response: ApiResponse) => ApiResponse
type ErrorInterceptor = (error: any) => void

// XML-RPC 认证信息
interface AuthInfo {
  uid: number
  password: string
  db: string
}

class RequestClient {
  private baseURL: string = ''
  private sessionId: string = ''
  private authInfo: AuthInfo | null = null
  private requestInterceptors: RequestInterceptor[] = []
  private responseInterceptors: ResponseInterceptor[] = []
  private errorInterceptors: ErrorInterceptor[] = []

  // 设置基础URL
  setBaseURL(url: string) {
    // #ifdef H5
    // H5 开发环境使用代理，不需要设置完整 URL
    if (import.meta.env.DEV) {
      this.baseURL = ''
      return
    }
    // #endif
    this.baseURL = url.replace(/\/$/, '')
  }

  // 设置Session ID
  setSessionId(sessionId: string) {
    this.sessionId = sessionId
  }

  // 获取Session ID
  getSessionId(): string {
    return this.sessionId
  }

  // 设置 XML-RPC 认证信息
  setAuthInfo(info: AuthInfo) {
    this.authInfo = info
  }

  // 获取认证信息
  getAuthInfo(): AuthInfo | null {
    return this.authInfo
  }

  // 清除Session
  clearSession() {
    this.sessionId = ''
    this.authInfo = null
  }

  // 添加请求拦截器
  addRequestInterceptor(interceptor: RequestInterceptor) {
    this.requestInterceptors.push(interceptor)
  }

  // 添加响应拦截器
  addResponseInterceptor(interceptor: ResponseInterceptor) {
    this.responseInterceptors.push(interceptor)
  }

  // 添加错误拦截器
  addErrorInterceptor(interceptor: ErrorInterceptor) {
    this.errorInterceptors.push(interceptor)
  }

  // 核心请求方法
  async request<T = any>(config: RequestConfig): Promise<T> {
    // 应用请求拦截器
    let finalConfig = { ...config }
    for (const interceptor of this.requestInterceptors) {
      finalConfig = interceptor(finalConfig)
    }

    // 判断是否是 XML-RPC 请求
    const isXmlRpc = finalConfig.url === '/jsonrpc' || finalConfig.data?.service

    // 构建完整URL
    let fullUrl = finalConfig.url
    if (!fullUrl.startsWith('http')) {
      fullUrl = `${this.baseURL}${finalConfig.url}`
    }

    // 构建请求头
    const headers: Record<string, string> = {
      'Content-Type': 'application/json',
      ...finalConfig.header
    }

    // 对于 session 认证，添加 Cookie
    if (this.sessionId && !isXmlRpc) {
      headers['Cookie'] = `session_id=${this.sessionId}`
    }

    // 对于 XML-RPC 请求，自动添加认证信息（如果需要）
    let requestData = finalConfig.data
    if (isXmlRpc && this.authInfo && requestData?.service === 'object') {
      // 确保 args 包含认证信息 [db, uid, password, model, method, ...]
      if (requestData.args) {
        const args = requestData.args
        if (args.length >= 4 && args[1] !== this.authInfo.uid) {
          // 自动注入认证信息到 execute_kw
          args.splice(0, 4, this.authInfo.db, this.authInfo.uid, this.authInfo.password, args[3])
        }
      }
    }

    return new Promise<T>((resolve, reject) => {
      uni.request({
        url: fullUrl,
        method: finalConfig.method || 'GET',
        data: requestData,
        header: headers,
        timeout: finalConfig.timeout || 30000,
        success: (res: any) => {
          try {
            // XML-RPC 直接返回 result，不需要包装在 ApiResponse 中
            if (isXmlRpc) {
              if (res.data?.error) {
                let errorMsg = res.data.error.data?.message || res.data.error.message || '请求失败'

                // 检测字段错误并给出更清晰的提示
                const errorData = res.data.error.data
                if (errorData?.debug) {
                  // 解析 Odoo 错误信息，提取字段名
                  const fieldMatch = errorData.debug.match(/Invalid field '([^']+)'/)
                  if (fieldMatch) {
                    errorMsg = `字段 "${fieldMatch[1]}" 不存在，请在 src/config/fields.ts 中移除此字段`
                  }
                }

                reject(new Error(errorMsg))
                return
              }
              // 确保 result 存在
              if (res.data?.result === undefined) {
                console.error('XML-RPC: result is undefined', res.data)
                reject(new Error('响应格式错误'))
                return
              }
              resolve(res.data.result as T)
              return
            }

            let response = res.data as ApiResponse<T>

            // 应用响应拦截器
            for (const interceptor of this.responseInterceptors) {
              response = interceptor(response)
            }

            // 检查错误
            if (response.error) {
              // 构建详细错误信息
              let errorMsg = response.error.message || '请求失败'
              if (response.error.data) {
                if (typeof response.error.data === 'string') {
                  errorMsg += ': ' + response.error.data
                } else if (response.error.data.message) {
                  errorMsg += ': ' + response.error.data.message
                } else if (response.error.data.debug) {
                  // 只显示第一行调试信息
                  const debugLines = response.error.data.debug.split('\n')
                  errorMsg += ': ' + debugLines[0]
                }
              }
              if (response.error.code) {
                errorMsg += ` (code: ${response.error.code})`
              }
              console.error('Odoo API Error:', response.error)
              const error = new Error(errorMsg)
              reject(error)
              return
            }

            resolve(response.result as T)
          } catch (e) {
            reject(new Error('响应解析失败'))
          }
        },
        fail: (err) => {
          // 应用错误拦截器
          for (const interceptor of this.errorInterceptors) {
            interceptor(err)
          }

          let errorMessage = '网络请求失败'
          if (err.errMsg) {
            if (err.errMsg.includes('timeout')) {
              errorMessage = '请求超时'
            } else if (err.errMsg.includes('fail')) {
              errorMessage = '网络连接失败'
            }
          }

          reject(new Error(errorMessage))
        }
      })
    })
  }

  // 便捷方法
  get<T = any>(url: string, data?: any): Promise<T> {
    return this.request<T>({ url, method: 'GET', data })
  }

  post<T = any>(url: string, data?: any): Promise<T> {
    return this.request<T>({ url, method: 'POST', data })
  }

  put<T = any>(url: string, data?: any): Promise<T> {
    return this.request<T>({ url, method: 'PUT', data })
  }

  delete<T = any>(url: string, data?: any): Promise<T> {
    return this.request<T>({ url, method: 'DELETE', data })
  }
}

// 创建单例
export const request = new RequestClient()

// 设置默认拦截器
request.addRequestInterceptor((config) => {
  // 可以在这里添加公共参数
  return config
})

request.addResponseInterceptor((response) => {
  // 可以在这里处理公共响应逻辑
  return response
})

request.addErrorInterceptor((error) => {
  console.error('请求错误:', error)
})
