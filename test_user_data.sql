-- 测试用户数据
-- 确保数据库中有用户记录，避免外键约束错误

-- 插入测试用户（如果不存在）
INSERT IGNORE INTO sys_user (id, phone, password, role_code, nickname, status, create_time) 
VALUES (1, '13800138000', 'e10adc3949ba59abbe56e057f20f883e', 'admin', '测试用户', 1, NOW());

-- 验证用户数据
SELECT * FROM sys_user WHERE id = 1;

-- 清理测试数据（可选）
-- DELETE FROM maintenance_records WHERE user_id = 1;
-- DELETE FROM sys_user WHERE id = 1;
