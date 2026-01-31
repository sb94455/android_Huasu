// 表单验证工具

// 验证规则接口
interface ValidationRule {
  required?: boolean
  message?: string
  validator?: (value: any) => boolean
  minLength?: number
  maxLength?: number
  pattern?: RegExp
}

// 验证结果接口
interface ValidationResult {
  valid: boolean
  message?: string
}

// 验证器类
class Validator {
  // 验证单个字段
  validate(value: any, rules: ValidationRule): ValidationResult {
    // 必填验证
    if (rules.required && (value === null || value === undefined || value === '')) {
      return {
        valid: false,
        message: rules.message || '此项为必填项'
      }
    }

    // 如果值为空且非必填，则通过验证
    if (!rules.required && (value === null || value === undefined || value === '')) {
      return { valid: true }
    }

    // 最小长度验证
    if (rules.minLength && typeof value === 'string' && value.length < rules.minLength) {
      return {
        valid: false,
        message: rules.message || `长度不能少于${rules.minLength}个字符`
      }
    }

    // 最大长度验证
    if (rules.maxLength && typeof value === 'string' && value.length > rules.maxLength) {
      return {
        valid: false,
        message: rules.message || `长度不能超过${rules.maxLength}个字符`
      }
    }

    // 正则验证
    if (rules.pattern && !rules.pattern.test(value)) {
      return {
        valid: false,
        message: rules.message || '格式不正确'
      }
    }

    // 自定义验证器
    if (rules.validator && !rules.validator(value)) {
      return {
        valid: false,
        message: rules.message || '验证失败'
      }
    }

    return { valid: true }
  }

  // 验证表单对象
  validateForm(data: Record<string, any>, rules: Record<string, ValidationRule>): {
    valid: boolean
    errors: Record<string, string>
  } {
    const errors: Record<string, string> = {}
    let valid = true

    for (const field in rules) {
      const result = this.validate(data[field], rules[field])
      if (!result.valid) {
        valid = false
        errors[field] = result.message || '验证失败'
      }
    }

    return { valid, errors }
  }
}

export const validator = new Validator()

// 常用验证规则
export const rules = {
  // 必填
  required: (message?: string): ValidationRule => ({
    required: true,
    message: message || '此项为必填项'
  }),

  // 用户名
  username: (message?: string): ValidationRule => ({
    required: true,
    pattern: /^[a-zA-Z0-9_]{3,20}$/,
    message: message || '用户名长度3-20位，只能包含字母、数字和下划线'
  }),

  // 密码
  password: (message?: string): ValidationRule => ({
    required: true,
    minLength: 6,
    message: message || '密码至少6位'
  }),

  // URL
  url: (message?: string): ValidationRule => ({
    required: true,
    pattern: /^https?:\/\/.+/,
    message: message || '请输入有效的URL地址'
  }),

  // 数据库名
  dbName: (message?: string): ValidationRule => ({
    required: true,
    pattern: /^[a-zA-Z][a-zA-Z0-9_]*$/,
    message: message || '数据库名必须以字母开头，只能包含字母、数字和下划线'
  }),

  // 手机号
  phone: (message?: string): ValidationRule => ({
    pattern: /^1[3-9]\d{9}$/,
    message: message || '请输入有效的手机号'
  }),

  // 邮箱
  email: (message?: string): ValidationRule => ({
    pattern: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
    message: message || '请输入有效的邮箱地址'
  })
}
