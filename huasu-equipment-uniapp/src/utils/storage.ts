// 本地存储工具封装

class Storage {
  // 设置数据
  async set(key: string, value: any): Promise<void> {
    return new Promise((resolve, reject) => {
      uni.setStorage({
        key,
        data: JSON.stringify(value),
        success: () => resolve(),
        fail: (err) => reject(err)
      })
    })
  }

  // 获取数据
  async get<T = any>(key: string): Promise<T | null> {
    return new Promise((resolve) => {
      uni.getStorage({
        key,
        success: (res) => {
          try {
            resolve(JSON.parse(res.data as string) as T)
          } catch {
            resolve(res.data as T)
          }
        },
        fail: () => resolve(null)
      })
    })
  }

  // 删除数据
  async remove(key: string): Promise<void> {
    return new Promise((resolve, reject) => {
      uni.removeStorage({
        key,
        success: () => resolve(),
        fail: (err) => reject(err)
      })
    })
  }

  // 清空所有数据
  async clear(): Promise<void> {
    return new Promise((resolve, reject) => {
      uni.clearStorage({
        success: () => resolve(),
        fail: (err) => reject(err)
      })
    })
  }

  // 获取存储信息
  async getInfo(): Promise<UniApp.GetStorageInfoSuccessCallbackResult> {
    return new Promise((resolve, reject) => {
      uni.getStorageInfo({
        success: (res) => resolve(res),
        fail: (err) => reject(err)
      })
    })
  }

  // 同步设置数据
  setSync(key: string, value: any): void {
    uni.setStorageSync(key, JSON.stringify(value))
  }

  // 同步获取数据
  getSync<T = any>(key: string): T | null {
    try {
      const data = uni.getStorageSync(key)
      return JSON.parse(data) as T
    } catch {
      return null
    }
  }

  // 同步删除数据
  removeSync(key: string): void {
    uni.removeStorageSync(key)
  }
}

export const storage = new Storage()
