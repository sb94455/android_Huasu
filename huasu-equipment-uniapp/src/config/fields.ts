// 设备模型字段配置 (基于 Odoo maintenance.equipment.re 模型)
export const EQUIPMENT_FIELDS = {
  // 基础字段
  ID: 'id',
  NAME: 'name',                  // 设备编号 (必填)
  EQUIPMENT_NAME: 'equipment_name',  // 设备名称 (必填)
  COMPLETE_NAME: 'complete_name',    // 完整名称 (计算字段)
  STATE: 'state',                // 设备状态: normal/bug/maintenance/scrapped

  // 设备信息
  EQUIPMENT_TYPE: 'equipment_type',  // 类型: production/office/testing/other
  LEVEL: 'level',                // 等级: a/b/c
  MODEL: 'model',                // 规格型号
  FACTORY_CODE: 'factory_code',  // 出厂编号
  ASSET_CODE: 'asset_code',      // 资产编号

  // 位置相关
  LOCATION_ID: 'location_id',    // 设备位置 (Many2one -> location.management)
  LOCATION: 'location',          // 位置号
  STORAGE_LOCATION: 'storage_location',  // 存放位置

  // 部门和人员
  DEPARTMENT_ID: 'department_id',    // 所属部门 (Many2one -> hr.department)
  RESPONSIBLE_ID: 'responsible_id',  // 负责人 (Many2one -> res.users)
  RELATED_PERSONNEL: 'related_personnel',  // 关联人员 (Many2many -> res.users)

  // 供应商相关
  BRAND_ID: 'brand_id',          // 品牌 (Many2one -> equipment.brand)
  SUPPLIER_ID: 'supplier_id',    // 供应商 (Many2one -> equipment.supplier)

  // 财务信息
  PURCHASE_DATE: 'purchase_date',    // 购置日期
  PURCHASE_AMOUNT: 'purchase_amount',// 购置金额
  WARRANTY_PERIOD: 'warranty_period',// 保修期(月)
  WARRANTY_END_DATE: 'warranty_end_date',  // 保修到期日 (计算)
  WARRANTY_STATUS: 'warranty_status',      // 保修状态 (计算)

  // 其他
  EXPECTED_SCRAP_DATE: 'expected_scrap_date',  // 预计报废日期
  QC_NUM: 'qc_num',              // 二维码字段
  NOTES: 'notes',                // 备注
  MAIN_IMAGE: 'main_image',      // 设备首页图
  DETAIL_IMAGES: 'detail_images', // 设备详情 (Many2many -> ir.attachment)

  // 布尔字段
  IS_MONITORED: 'is_monitored',  // 是否监控
  IS_CONTROLLED: 'is_controlled',// 是否管控

  // 计算字段
  EQUIPMENT_AGE: 'equipment_age',    // 设备年龄(年)
  DAYS_TO_SCRAP: 'days_to_scrap',    // 距报废天数

  // 标准字段
  CREATE_DATE: 'create_date',
  WRITE_DATE: 'write_date',
  CREATE_UID: 'create_uid',
  WRITE_UID: 'write_uid',
} as const

// 设备列表默认显示的字段
export const EQUIPMENT_LIST_FIELDS = [
  EQUIPMENT_FIELDS.ID,
  EQUIPMENT_FIELDS.NAME,
  EQUIPMENT_FIELDS.EQUIPMENT_NAME,
  EQUIPMENT_FIELDS.COMPLETE_NAME,
  EQUIPMENT_FIELDS.STATE,
  EQUIPMENT_FIELDS.MODEL,
  EQUIPMENT_FIELDS.BRAND_ID,
  EQUIPMENT_FIELDS.LOCATION_ID,
  EQUIPMENT_FIELDS.DEPARTMENT_ID,
]

// 设备详情显示的字段
export const EQUIPMENT_DETAIL_FIELDS = [
  EQUIPMENT_FIELDS.ID,
  EQUIPMENT_FIELDS.NAME,
  EQUIPMENT_FIELDS.EQUIPMENT_NAME,
  EQUIPMENT_FIELDS.COMPLETE_NAME,
  EQUIPMENT_FIELDS.STATE,
  EQUIPMENT_FIELDS.EQUIPMENT_TYPE,
  EQUIPMENT_FIELDS.LEVEL,
  EQUIPMENT_FIELDS.MODEL,
  EQUIPMENT_FIELDS.FACTORY_CODE,
  EQUIPMENT_FIELDS.ASSET_CODE,
  EQUIPMENT_FIELDS.LOCATION_ID,
  EQUIPMENT_FIELDS.LOCATION,
  EQUIPMENT_FIELDS.STORAGE_LOCATION,
  EQUIPMENT_FIELDS.DEPARTMENT_ID,
  EQUIPMENT_FIELDS.RESPONSIBLE_ID,
  EQUIPMENT_FIELDS.RELATED_PERSONNEL,
  EQUIPMENT_FIELDS.BRAND_ID,
  EQUIPMENT_FIELDS.SUPPLIER_ID,
  EQUIPMENT_FIELDS.PURCHASE_DATE,
  EQUIPMENT_FIELDS.PURCHASE_AMOUNT,
  EQUIPMENT_FIELDS.WARRANTY_PERIOD,
  EQUIPMENT_FIELDS.WARRANTY_END_DATE,
  EQUIPMENT_FIELDS.WARRANTY_STATUS,
  EQUIPMENT_FIELDS.EXPECTED_SCRAP_DATE,
  EQUIPMENT_FIELDS.EQUIPMENT_AGE,
  EQUIPMENT_FIELDS.DAYS_TO_SCRAP,
  EQUIPMENT_FIELDS.QC_NUM,
  EQUIPMENT_FIELDS.NOTES,
  EQUIPMENT_FIELDS.CREATE_DATE,
  EQUIPMENT_FIELDS.WRITE_DATE,
]

// 点检任务字段
export const INSPECTION_FIELDS = {
  ID: 'id',
  NAME: 'name',
  STATE: 'state',
  CREATE_DATE: 'create_date',
  EQUIPMENT_ID: 'equipment_id',
  EQUIPMENT_NAME: 'equipment_name',
  INSPECTOR_ID: 'inspector_id',
  INSPECTOR_NAME: 'inspector_name',
  NOTES: 'notes',
} as const

// 报修单字段
export const REPAIR_FIELDS = {
  ID: 'id',
  NAME: 'name',
  STATE: 'state',
  PRIORITY: 'priority',
  FAULT_TYPE: 'fault_type',
  FAULT_DESCRIPTION: 'fault_description',
  EQUIPMENT_ID: 'equipment_id',
  EQUIPMENT_NAME: 'equipment_name',
  CREATE_DATE: 'create_date',
  NOTES: 'notes',
} as const
