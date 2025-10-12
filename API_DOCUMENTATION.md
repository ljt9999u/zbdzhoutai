# 保养记录图片上传接口文档

## 概述
本文档描述了保养记录系统的图片上传功能接口。系统支持为保养记录添加多张图片，图片以JSON数组形式存储在数据库中。

## 数据库表结构
```sql
CREATE TABLE maintenance_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '保养记录ID',
    user_id BIGINT NOT NULL COMMENT '用户ID，关联sys_user表',
    product_name VARCHAR(100) NOT NULL COMMENT '产品名称',
    product_code VARCHAR(50) COMMENT '产品编码',
    maintenance_type ENUM('日常清洁', '防潮处理', '结构检查', '深度保养') NOT NULL COMMENT '保养类型',
    maintenance_date DATE NOT NULL COMMENT '保养日期',
    description TEXT NOT NULL COMMENT '保养内容描述',
    images JSON COMMENT '保养图片URL数组',
    status TINYINT DEFAULT 1 COMMENT '保养状态：1待处理 2处理中 3已完成',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES sys_user(id)
);
```

## API接口

### 1. 添加保养记录（不包含图片）
**接口地址：** `POST /maintenance/add`

**请求参数：**
```json
{
    "productName": "产品名称",
    "productCode": "产品编码（可选）",
    "maintenanceType": "日常清洁",
    "maintenanceDate": "2024-01-01",
    "description": "保养内容描述"
}
```

**响应示例：**
```json
{
    "code": 200,
    "msg": "保养记录添加成功",
    "data": {
        "id": 1,
        "userId": 1,
        "productName": "产品名称",
        "productCode": "产品编码",
        "maintenanceType": "日常清洁",
        "maintenanceDate": "2024-01-01",
        "description": "保养内容描述",
        "images": null,
        "status": 1,
        "createTime": "2024-01-01T10:00:00",
        "updateTime": "2024-01-01T10:00:00"
    }
}
```

### 2. 添加保养记录并上传图片
**接口地址：** `POST /maintenance/addWithImages`

**请求参数：** `multipart/form-data`
- `productName`: 产品名称（必填）
- `productCode`: 产品编码（可选）
- `maintenanceType`: 保养类型（必填）
- `maintenanceDate`: 保养日期（必填，格式：yyyy-MM-dd）
- `description`: 保养内容描述（必填）
- `files`: 图片文件数组（可选，支持多文件上传）

**响应示例：**
```json
{
    "code": 200,
    "msg": "保养记录添加成功",
    "data": {
        "id": 1,
        "userId": 1,
        "productName": "产品名称",
        "productCode": "产品编码",
        "maintenanceType": "日常清洁",
        "maintenanceDate": "2024-01-01",
        "description": "保养内容描述",
        "images": ["/uploads/uuid1.jpg", "/uploads/uuid2.jpg"],
        "status": 1,
        "createTime": "2024-01-01T10:00:00",
        "updateTime": "2024-01-01T10:00:00"
    }
}
```

### 3. 为现有保养记录上传图片
**接口地址：** `POST /maintenance/uploadImages/{id}`

**请求参数：** `multipart/form-data`
- `files`: 图片文件数组（必填）

**响应示例：**
```json
{
    "code": 200,
    "msg": "图片上传成功",
    "data": ["/uploads/uuid1.jpg", "/uploads/uuid2.jpg"]
}
```

### 4. 查询保养记录
**接口地址：** `GET /maintenance/{id}`

**响应示例：**
```json
{
    "code": 200,
    "msg": "查询成功",
    "data": {
        "id": 1,
        "userId": 1,
        "productName": "产品名称",
        "productCode": "产品编码",
        "maintenanceType": "日常清洁",
        "maintenanceDate": "2024-01-01",
        "description": "保养内容描述",
        "images": ["/uploads/uuid1.jpg", "/uploads/uuid2.jpg"],
        "status": 1,
        "createTime": "2024-01-01T10:00:00",
        "updateTime": "2024-01-01T10:00:00"
    }
}
```

### 5. 根据用户ID查询保养记录列表
**接口地址：** `GET /maintenance/user/{userId}`

**响应示例：**
```json
{
    "code": 200,
    "msg": "查询成功",
    "data": [
        {
            "id": 1,
            "userId": 1,
            "productName": "产品名称",
            "productCode": "产品编码",
            "maintenanceType": "日常清洁",
            "maintenanceDate": "2024-01-01",
            "description": "保养内容描述",
            "images": ["/uploads/uuid1.jpg", "/uploads/uuid2.jpg"],
            "status": 1,
            "createTime": "2024-01-01T10:00:00",
            "updateTime": "2024-01-01T10:00:00"
        }
    ]
}
```

### 6. 查询所有保养记录
**接口地址：** `GET /maintenance/all`

### 7. 更新保养记录
**接口地址：** `PUT /maintenance/update`

### 8. 删除保养记录
**接口地址：** `DELETE /maintenance/{id}`

## 配置说明

### 文件上传配置
在 `application.yml` 中配置了文件上传相关参数：
```yaml
spring:
  servlet:
    multipart:
      max-file-size: 10MB      # 单个文件最大大小
      max-request-size: 50MB   # 请求最大大小
      enabled: true

file:
  upload:
    path: ./uploads/           # 文件上传路径
```

### 静态资源访问
配置了静态资源访问路径，上传的图片可以通过以下URL访问：
- 访问路径：`http://localhost:9096/uploads/{filename}`
- 实际文件路径：`./uploads/{filename}`

## 技术实现

### 1. 实体类
- `MaintenanceRecord`: 保养记录实体类，包含所有字段和JSON序列化注解

### 2. 数据访问层
- `MaintenanceRecordMapper`: 使用MyBatis注解方式实现数据访问
- `JsonTypeHandler`: 自定义类型处理器，处理List<String>与JSON字符串的转换

### 3. 服务层
- `MaintenanceRecordService`: 服务接口
- `MaintenanceRecordServiceImpl`: 服务实现类，包含文件上传逻辑

### 4. 控制层
- `MaintenanceRecordController`: REST API控制器
- `WebConfig`: 静态资源访问配置

## 使用示例

### 前端上传示例（JavaScript）
```javascript
// 添加保养记录并上传图片
const formData = new FormData();
formData.append('productName', '测试产品');
formData.append('maintenanceType', '日常清洁');
formData.append('maintenanceDate', '2024-01-01');
formData.append('description', '测试保养内容');
formData.append('files', file1);
formData.append('files', file2);

fetch('/maintenance/addWithImages', {
    method: 'POST',
    body: formData
})
.then(response => response.json())
.then(data => console.log(data));
```

### 测试接口
访问 `GET /test/maintenance` 可以测试接口是否正常工作。

## 注意事项

1. 文件上传大小限制为10MB，总请求大小限制为50MB
2. 图片文件会生成UUID作为文件名，避免重名冲突
3. 上传的图片URL格式为 `/uploads/{uuid}.{extension}`
4. 数据库中的images字段存储JSON格式的URL数组
5. 需要确保uploads目录有写入权限
